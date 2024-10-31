package com.hugov.fuyuki.sheets.batch.processor

import org.springframework.batch.item.ItemProcessor
import com.hugov.fuyuki.sheets.model.data.IdRow
import com.hugov.fuyuki.sheets.model.data.Row
import com.hugov.fuyuki.sheets.model.connecteur.ScriptRequestRayshift
import com.hugov.fuyuki.sheets.model.connecteur.ScriptRequestAtlas
import com.hugov.fuyuki.sheets.model.parser.ParserAssembler

class RowItemProcessor extends ItemProcessor[IdRow, Row] {
    override def process(item: IdRow): Row = {
        val rowRayshift: Row = ScriptRequestRayshift.getRowScript(item)
        val rowAtlas: Row = ScriptRequestAtlas.getRowScript(item)
        Row(rowRayshift.idRow, ParserAssembler.associateRayshiftAtlas(rowRayshift.lines, rowAtlas.lines))
    }

    
}