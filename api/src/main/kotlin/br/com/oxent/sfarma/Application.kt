package br.com.oxent.sfarma

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    //System.out.println(LocalDateTime.now())
}
