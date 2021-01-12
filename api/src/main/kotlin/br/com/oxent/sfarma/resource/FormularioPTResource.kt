package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.entity.Formulario
import br.com.oxent.sfarma.model.service.FormularioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/formularios")
class FormularioPTResource @Autowired
constructor(private val service: FormularioService) {

    @PostMapping()
    fun set(@RequestBody f: Formulario): ResponseEntity<Formulario> {
        service.set(f)
        return ResponseEntity.ok<Formulario>(f)
    }

    @GetMapping
    fun list() = service.list()

    @GetMapping("/respostas/{id}")
    fun listRespostas(@PathVariable id: Int) = service.listResposta(id)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remover(@PathVariable id: Int) = service.delete(id)

    @PostMapping("/respostas")
    fun update(@RequestBody formulario: Formulario) = service.loadQuestions(formulario)


}