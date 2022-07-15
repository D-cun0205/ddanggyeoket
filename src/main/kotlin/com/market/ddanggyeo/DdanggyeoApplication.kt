package com.market.ddanggyeo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class DdanggyeoApplication

fun main(args: Array<String>) {
    runApplication<DdanggyeoApplication>(*args)
}
