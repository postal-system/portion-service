package io.codero.portionservice.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object PortionUtil {
    fun Any.getLogger(): Logger = LoggerFactory.getLogger(javaClass)
}
