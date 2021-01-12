package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.entity.Ocorrencia
import br.com.oxent.sfarma.model.service.OcorrenciaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid


@RestController
@RequestMapping("/api/ocorrencias")
class OcorrenciaResource @Autowired
constructor(private val ocorrenciaService: OcorrenciaService) {

    @GetMapping
    fun getList() = ResponseEntity.ok(ocorrenciaService.getList())

    @GetMapping("/setor")
    fun getList(principal: Principal) = ResponseEntity.ok(ocorrenciaService.getListOcorrenciaDoSetor(principal.name))

    @GetMapping("/total")
    fun getTotal() = ResponseEntity.ok(ocorrenciaService.getTotal())

    @PostMapping
    fun set(@Valid @RequestBody ocorrencia: Ocorrencia): ResponseEntity<Ocorrencia> {
        ocorrenciaService.set(ocorrencia)
        return ResponseEntity.ok<Ocorrencia>(ocorrencia)
    }
}