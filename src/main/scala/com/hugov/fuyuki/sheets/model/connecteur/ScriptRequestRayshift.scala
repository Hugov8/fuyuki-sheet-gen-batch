package com.hugov.fuyuki.sheets.model.connecteur

import com.hugov.fuyuki.sheets.model.parser.RayshiftParser
import com.hugov.fuyuki.sheets.model.exception.ApplicationException

object ScriptRequestRayshift extends ScriptRequest {
    val key = sys.env.get("API_KEY_RAYSHIFT") match {
        case Some(value) => value
        case None => throw new ApplicationException("Key API_KEY_RAYSHIFT not found")
    }
    override val uri = "https://rayshift.io/api/v1/translate/script/forward/"
    override val parser = RayshiftParser
    override def buildURL(idRow: String): String = uri + idRow + "?apiKey="+key
}