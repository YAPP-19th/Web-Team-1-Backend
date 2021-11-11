package com.yapp.giljob

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
class GiljobApplication

fun main(args: Array<String>) {
    runApplication<GiljobApplication>(*args)
}
