package com.hugov.fuyuki.sheets.model.service

import com.hugov.fuyuki.sheets.model.connecteur.WarRequest
import com.hugov.fuyuki.sheets.model.data.Row
import com.hugov.fuyuki.sheets.model.connecteur.ScriptRequest
import com.hugov.fuyuki.sheets.model.connecteur.ScriptRequestRayshift
import com.hugov.fuyuki.sheets.model.connecteur.WarRequestAtlas
import com.hugov.fuyuki.sheets.model.data.War
import com.hugov.fuyuki.sheets.model.connecteur.sheets.SheetsServiceUtil
import com.hugov.fuyuki.sheets.model.connecteur.sheets.SpreadSheetUtil
import com.google.api.services.sheets.v4.model.Spreadsheet
import com.hugov.fuyuki.sheets.model.connecteur.sheets.UpdateSheetUtil
import com.hugov.fuyuki.sheets.model.connecteur.sheets.DriveUtil
import com.typesafe.scalalogging._
import com.hugov.fuyuki.sheets.model.connecteur.ScriptRequestAtlas
import com.hugov.fuyuki.sheets.model.parser.ParserAssembler
import com.hugov.fuyuki.sheets.model.util.Logging

trait ScriptService extends Logging {
    val warRequester: WarRequest
    val scriptRequester: ScriptRequest
    def generateWar(idWar: String, mail: String): String
    def updateWar(idWar: String, idSpreadSheet: String): String
    def updateWar(idWar: String, idSpreadSheet: String, addSheet: Boolean): String
}

object ScriptServiceImpl extends ScriptService {
    val skipRow: List[String] = List("300091351")
    override val scriptRequester: ScriptRequest = ScriptRequestRayshift
    override val warRequester: WarRequest = WarRequestAtlas
    override def generateWar(idWar: String, mail: String): String = {
        logger.info(s"Démarrage de la génération de la war $idWar pour $mail")
        val war: War = warRequester.getWarFromId(idWar)
        val spreadSheet: Spreadsheet = SpreadSheetUtil.createSpreadSheet(war.name)
        DriveUtil.shareSpreadsheet(spreadSheet.getSpreadsheetId(), mail)
        updateWar(idWar, spreadSheet.getSpreadsheetId(), true)
    }

    override def updateWar(idWar: String, idSpreadSheet: String, addSheet: Boolean = false): String = {
        logger.info(s"Démarrage de la mise à jour de la sheet $idSpreadSheet pour la war $idWar")
        val war: War = warRequester.getWarFromId(idWar)
        war.idRows.filter(x => x.id.substring(0,2)!="91" && !skipRow.contains(x.id)).foreach(id => {
            val rowRayshift: Row = scriptRequester.getRowScript(id)
            val rowAtlas: Row = ScriptRequestAtlas.getRowScript(id)
            val row: Row = Row(rowRayshift.idRow, ParserAssembler.associateRayshiftAtlas(rowRayshift.lines, rowAtlas.lines))
            if(addSheet){
                UpdateSheetUtil.addSheet(row.idRow.id, idSpreadSheet)
            }
            UpdateSheetUtil.sendRow2Sheet(row, idSpreadSheet)
        })
        SheetsServiceUtil.baseURISheet+idSpreadSheet+"/edit"
    }

    override def updateWar(idWar: String, idSpreadSheet: String): String = updateWar(idWar, idSpreadSheet, false)
}