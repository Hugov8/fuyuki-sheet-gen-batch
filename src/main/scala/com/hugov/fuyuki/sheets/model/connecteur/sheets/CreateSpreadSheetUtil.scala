package com.hugov.fuyuki.sheets.model.connecteur.sheets

import com.google.api.services.sheets.v4.model.Spreadsheet
import com.google.api.services.sheets.v4.model.SpreadsheetProperties
import com.hugov.fuyuki.sheets.model.exception.ConnexionException
import com.typesafe.scalalogging._
import com.hugov.fuyuki.sheets.model.util.Logging

trait SpreadSheetUtilAbstractForm {
    val sheetService = SheetsServiceUtil.getSheetsService
    def createSpreadSheet(war: String): Spreadsheet
}

object SpreadSheetUtil extends SpreadSheetUtilAbstractForm with ExecutionSheet with Logging {
    override def createSpreadSheet(idWar: String): Spreadsheet = {
        val spreadSheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(idWar))
        execute(sheetService.spreadsheets().create(spreadSheet)) match {
            case Some(x) => logger.info(s"Spreadsheet créé $x");x
            case None => throw new ConnexionException(s"Création échoué pour war = $idWar")
        }
    }
}