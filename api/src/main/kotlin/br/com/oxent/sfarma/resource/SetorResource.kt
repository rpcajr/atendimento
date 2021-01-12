package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.entity.Setor
import br.com.oxent.sfarma.model.service.SetorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid


@RestController
@RequestMapping("/api/setores")
class SetorResource @Autowired
constructor(private val setorService: SetorService) {

    @GetMapping
    fun getList() = ResponseEntity.ok(setorService.getList())

    @GetMapping("/dependentes")
    fun getListUsuario(principal: Principal) = ResponseEntity.ok(setorService.getListUsuario(principal.name))

    @GetMapping("/organograma")
    fun getOrganograma() = ResponseEntity.ok(setorService.getOrganograma())

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) = ResponseEntity.status(HttpStatus.OK).body(setorService.get(id))

    @PostMapping
    fun setSetor(@Valid @RequestBody setor: Setor): ResponseEntity<Setor> {
        setorService.set(setor)
        return ResponseEntity.ok<Setor>(setor)
    }


}