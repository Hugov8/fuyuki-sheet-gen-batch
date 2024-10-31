package com.hugov.fuyuki.sheets.model.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

trait Logging {
    protected val logger: Logger = LoggerFactory.getLogger(getClass.getName)
}