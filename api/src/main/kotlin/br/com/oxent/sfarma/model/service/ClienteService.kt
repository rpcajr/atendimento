package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.entity.Cliente
import br.com.oxent.sfarma.model.repository.ClienteDAO
import br.com.oxent.sfarma.resource.handler.JFException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ExceptionHandler


@Service
@Transactional(readOnly = true)
class ClienteService @Autowired
constructor(private val dao: ClienteDAO, private val contatoService: ContatoService) {

    fun getList() = dao.getList()
    fun getTotal() = dao.getTotal()
    fun getClientePesquisa(query: String): List<Cliente> {
        return try {
            dao.getClientePesquisa(query)
        } catch (e: EmptyResultDataAccessException) {
            emptyList()
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @ExceptionHandler(JFException::class)
    fun set(cliente: Cliente) {
        if (cliente.id == null) {
            cliente.id = getNextID()
            verificarExistenciaCodCliente(cliente)
            dao.inserir(cliente)
        } else {
            verificarExistenciaCodCliente(cliente)
            dao.atualizar(cliente)
        }
        contatoService.setContatos(cliente)
    }


    @ExceptionHandler(JFException::class)
    private fun verificarExistenciaCodCliente(cliente: Cliente) {
        val c = try {
            dao.getCliente(cliente.codCliente)
        } catch (e: EmptyResultDataAccessException) {
            null
        }

        if (c != null && c.id != cliente.id) {
            throw JFException("o Registro (${c.codCliente}) Informado já está cadastrado para ${c.nome}")
        }
    }

    fun getCliente(id: Int): Cliente? {
        val c = dao.getCliente(id)
        c?.let {
            c.contatos = contatoService.getContatos(it.id!!)
        }
        return c
    }

    fun getCliente(codCliente: String): Cliente? {
        val c = dao.getCliente(codCliente)
        c?.let {
            c.contatos = contatoService.getContatos(it.id!!)
        }
        return c
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun atualizarJFDados(cliente: Cliente) {
        try {
            dao.getCliente(cliente.codCliente)
            dao.atualizarJFDados(cliente)
        } catch (e: EmptyResultDataAccessException) {
            set(cliente)
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun getNextID() = dao.getNextID()

    @Transactional(propagation = Propagation.REQUIRED)
    fun atualizaVersao(clienteID: Int, versao: String) {
        dao.atualizaVersao(clienteID, versao)
    }

    fun getClienteVersao(versao: String) = dao.getClienteVersao(versao)
}