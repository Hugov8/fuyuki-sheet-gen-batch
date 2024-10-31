package com.hugov.fuyuki.sheets.batch.tasklet

import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import com.hugov.fuyuki.sheets.model.connecteur.sheets.SpreadSheetUtil
import com.hugov.fuyuki.sheets.model.connecteur.sheets.DriveUtil
import com.hugov.fuyuki.sheets.batch.util.BatchConstantes

class CreateSpreadsheetTasklet(val mail: String, val idWar: String) extends Tasklet {

  override def execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus = {
    val spreadSheet = SpreadSheetUtil.createSpreadSheet(idWar)
    DriveUtil.shareSpreadsheet(spreadSheet.getSpreadsheetId(), mail)
    //chunkContext.getStepContext().getJobExecutionContext().put(BatchConstantes.ID_SPREADSHEET, spreadSheet.getSpreadsheetId())
    RepeatStatus.FINISHED
  }

    
}