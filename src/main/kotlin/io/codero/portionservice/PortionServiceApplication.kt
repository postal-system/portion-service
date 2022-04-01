package io.codero.portionservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@SpringBootApplication
class PortionServiceApplication
fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    SpringApplication.run(PortionServiceApplication::class.java, *args)
}
