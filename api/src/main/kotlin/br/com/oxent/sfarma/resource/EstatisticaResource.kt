package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.service.EstatisticaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/estatisticas")
class EstatisticaResource @Autowired
constructor(private val estatisticaService: EstatisticaService) {

    @GetMapping("/versao")
    fun getListVersao() = estatisticaService.getListVersao()


}