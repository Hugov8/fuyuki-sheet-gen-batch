package com.hugov.fuyuki.sheets.model.connecteur

import requests._
import com.hugov.fuyuki.sheets.model.data.Row
import com.hugov.fuyuki.sheets.model.data.Line
import com.hugov.fuyuki.sheets.model.parser.Parser
import com.hugov.fuyuki.sheets.model.exception.ConnexionException
import com.hugov.fuyuki.sheets.model.data.IdRow

trait ScriptRequest {
    val uri: String
    val parser: Parser[Line]
    /**
      * 
      *
      * @param params en premier l'id de la row, en deuxième l'id de la war, en 3e NA ou JP
      * @return l'url à appeler
      */
    def buildURL(idRow: String): String
    def getRowScriptText(idRow : String) : String = try {
        val res = requests.get(buildURL(idRow), readTimeout = 100_000, sslContext=SSLContextConfig.getInstance(), verifySslCerts=false)
        res.statusCode match {
          case 200 => res.text()
          case _ => throw new ConnexionException(s"Erreur lors de l'appel à $uri pour $idRow : ${res.text()}")
        }
      } catch {
        case e: Throwable => throw new ConnexionException(e.toString())
    }
    
    def getRowScript(idRow: IdRow) = Row(idRow, parser.parse(getRowScriptText(idRow.scriptFileName)))
}