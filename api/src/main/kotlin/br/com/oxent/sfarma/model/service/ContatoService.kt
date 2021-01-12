package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.entity.Cliente
import br.com.oxent.sfarma.model.entity.Contato
import br.com.oxent.sfarma.model.repository.ContatoDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class ContatoService @Autowired
constructor(private val dao: ContatoDAO) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun getNextID() = dao.getNextID()

    fun getContatos(clienteID: Int): List<Contato> {
        return try {
            dao.getContatos(clienteID)
        } catch (e: EmptyResultDataAccessException) {
            emptyList()
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun setContato(contato: Contato, id: Int?) {
        if (contato.id == null) {
            contato.id = getNextID()
        }
        contato.clienteID = id
        dao.set(contato)
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun setContatos(cliente: Cliente) {
        cliente.contatos?.let {
            for (c in it) {
                setContato(c, cliente.id)
            }
        }
    }

}