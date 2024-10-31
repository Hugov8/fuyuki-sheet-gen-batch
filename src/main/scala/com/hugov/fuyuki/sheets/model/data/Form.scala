package com.hugov.fuyuki.sheets.model.data

case class GenerationSheetForm(val idWar: String, val mail: String)
case class UpdateSheetForm(val urlSpreadsheet: String, val idWar: String)