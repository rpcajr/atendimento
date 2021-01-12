package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.service.ClienteDTOService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/clientes")
class ClienteDTOResource @Autowired
constructor(private val service: ClienteDTOService) {
    @GetMapping
    fun getList() = service.getList()

    @GetMapping("/{id}")
    fun getCliente(@PathVariable id: Int) = service.getCliente(id)

    @GetMapping("/total")
    fun getTotal() = service.getCount()

    @PostMapping("/{id}")
    fun update(@PathVariable id: Int) = service.update(id)

}