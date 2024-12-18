package com.hugov.fuyuki.sheets.batch.reader

import org.springframework.batch.item.ItemReader
import com.hugov.fuyuki.sheets.model.connecteur.ScriptRequest
import com.hugov.fuyuki.sheets.model.connecteur.ScriptRequestRayshift
import com.hugov.fuyuki.sheets.model.data.IdRow


class RowIdReader(var items: List[IdRow]) extends ItemReader[IdRow] {

    val scriptRequester: ScriptRequest = ScriptRequestRayshift

    override def read(): IdRow = items match {
        case e::x => items = x; e
        case Nil => null 
    }
}