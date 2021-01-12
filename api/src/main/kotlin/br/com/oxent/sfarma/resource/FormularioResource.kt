package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.entity.Formulario
import br.com.oxent.sfarma.model.service.FormularioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/formulario")
class FormularioResource @Autowired
constructor(private val service: FormularioService) {

    @GetMapping("/{uuid}")
    fun get(@PathVariable uuid: String) = service.get(uuid)

    @PostMapping("/responder")
    @Async
    fun set(@RequestBody f: Formulario): ResponseEntity<Formulario> {
        service.responder(f)
        return ResponseEntity.ok<Formulario>(f)
    }


}