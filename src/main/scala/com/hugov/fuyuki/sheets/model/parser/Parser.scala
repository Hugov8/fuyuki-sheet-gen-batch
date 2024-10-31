package com.hugov.fuyuki.sheets.model.parser

trait Parser[T] {
    def parse(script : String): List[T]
    def unparse(res: List[T]): String
}