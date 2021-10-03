package com.yapp.giljob.global.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Log {
    companion object {
        private val log : Logger get() = LoggerFactory.getLogger(this::class.java)

        fun error(message: String?) {
            log.error(message)
        }
    }
}