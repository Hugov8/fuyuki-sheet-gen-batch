package com.hugov.fuyuki.sheets.model.util

import com.typesafe.scalalogging._

trait Logging {
    protected val logger: Logger = Logger(getClass.getName)
}