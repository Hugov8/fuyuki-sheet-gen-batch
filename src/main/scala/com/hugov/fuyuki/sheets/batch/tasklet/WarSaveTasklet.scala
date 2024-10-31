package com.hugov.fuyuki.sheets.batch.tasklet

import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import com.hugov.fuyuki.sheets.model.connecteur.sheets.SpreadSheetUtil
import com.hugov.fuyuki.sheets.model.connecteur.sheets.DriveUtil
import com.hugov.fuyuki.sheets.model.connecteur.WarRequestAtlas
import com.hugov.fuyuki.sheets.model.connecteur.WarRequest
import com.hugov.fuyuki.sheets.batch.util.WarContext
import com.hugov.fuyuki.sheets.model.data.War

class WarSaveTasklet(val idWar: String, val warContext: WarContext) extends Tasklet {
    val warRequester: WarRequest = WarRequestAtlas
    val skipRow: List[String] = List("300091351")
    
    override def execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus = {
        val war = warRequester.getWarFromId(idWar)
        warContext.war = War(war.id, war.name, war.idRows.filter(x => x.id.substring(0,2)!="91" && !skipRow.contains(x.id)))
        RepeatStatus.FINISHED
    }

    
}