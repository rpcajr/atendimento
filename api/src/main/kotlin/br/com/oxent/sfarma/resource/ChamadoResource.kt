package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.entity.Chamado
import br.com.oxent.sfarma.model.entity.Filter
import br.com.oxent.sfarma.model.service.ChamadoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid


@RestController
@RequestMapping("/api/chamados")
class ChamadoResource @Autowired
constructor(private val service: ChamadoService) {

    @PostMapping
    fun set(@Valid @RequestBody o: Chamado): ResponseEntity<Chamado> {
        service.set(o)
        return ResponseEntity.ok<Chamado>(o)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) = service.get(id)

    @GetMapping
    fun list(f: Filter?, p: Pageable, principal: Principal) = service.list(f, p, principal.name)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable id: Int) = service.delete(id)

    @GetMapping("/relatorio")
    @Throws(Exception::class)
    fun relatorioPorPessoa(f: Filter?, principal: Principal): ResponseEntity<ByteArray?>? {
        val relatorio: ByteArray? = service.relatorio(f , principal.name)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .body(relatorio)
    }


}