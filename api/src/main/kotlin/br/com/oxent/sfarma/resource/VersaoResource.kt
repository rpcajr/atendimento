package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.entity.Versao
import br.com.oxent.sfarma.model.service.VersaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/versoes")
class VersaoResource @Autowired
constructor(private val v: VersaoService) {

    @GetMapping
    fun getList() = ResponseEntity.ok(v.getList())

    @GetMapping("/total")
    fun getTotal() = ResponseEntity.ok(v.getTotal())

    @PostMapping
    fun set(@Valid @RequestBody versao: Versao): ResponseEntity<Versao> {
        v.set(versao)
        return ResponseEntity.ok<Versao>(versao)
    }


}