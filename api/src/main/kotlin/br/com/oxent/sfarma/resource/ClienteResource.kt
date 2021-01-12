package br.com.oxent.sfarma.resource

import br.com.oxent.sfarma.model.entity.Cliente
import br.com.oxent.sfarma.model.entity.Contato
import br.com.oxent.sfarma.model.entity.Ocorrencia
import br.com.oxent.sfarma.model.service.ClienteService
import br.com.oxent.sfarma.model.service.ContatoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/clientes")
class ClienteResource @Autowired
constructor(private val service: ClienteService, private val contatoService: ContatoService) {

    @GetMapping
    fun getList() = ResponseEntity.ok(service.getList())

    @GetMapping("/total")
    fun getTotal() = ResponseEntity.ok(service.getTotal())

    @GetMapping("/contatos/{id}")
    fun getContatosCliente(@PathVariable id: Int) = contatoService.getContatos(id)

    @PostMapping("/contatos/{id}")
    fun setContatosCliente(@PathVariable id: Int, @RequestBody contato: Contato): ResponseEntity<Contato> {
        contatoService.setContato(contato, id)
        return ResponseEntity.ok<Contato>(contato)
    }

    @GetMapping("/{id}")
    fun getCliente(@PathVariable id: Int) = service.getCliente(id)

    @GetMapping("/versao/{versao}")
    fun getClienteVersao(@PathVariable versao:String) = service.getClienteVersao(versao)

    @GetMapping("/cod_cliente/{codCliente}")
    fun getClientePorCodigo(@PathVariable codCliente: String) = service.getCliente(codCliente)

    @GetMapping("/pesquisa/{query}")
    fun getClientePesquisa(@PathVariable query: String) = service.getClientePesquisa(query)

    @PostMapping
    fun set(@Valid @RequestBody o: Cliente): ResponseEntity<Cliente> {
        service.set(o)
        return ResponseEntity.ok<Cliente>(o)
    }


}