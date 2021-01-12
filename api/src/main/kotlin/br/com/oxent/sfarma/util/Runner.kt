package br.com.oxent.sfarma.util

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component


@Component
class Runner(val migradorService: MigracaoService) : CommandLineRunner {

    @Throws(Exception::class)
    override fun run(vararg args: String) {
        migradorService.migrar()
    }


}
