package com.example.custard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustardApplication

fun main(args: Array<String>) {
    runApplication<CustardApplication>(*args)
}
