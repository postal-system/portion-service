package io.codero.portionservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PortionServiceApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<PortionServiceApplication>(*args)
}
