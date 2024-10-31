package com.hugov.fuyuki.sheets.batch.writer

import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.Chunk
import com.hugov.fuyuki.sheets.model.data.Row
import com.hugov.fuyuki.sheets.model.connecteur.sheets.UpdateSheetUtil

class RowItemWriter(val idSpreadSheet: String) extends ItemWriter[Row] {
    override def write(chunk: Chunk[_ <: Row]): Unit = chunk.getItems().forEach(row => UpdateSheetUtil.sendRow2Sheet(row, idSpreadSheet))
    
}