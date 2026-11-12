package com.bytebites.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ByteBitesApplication

fun main(args: Array<String>) {
    runApplication<ByteBitesApplication>(*args)
}
