package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.entity.Setor
import br.com.oxent.sfarma.model.repository.SetorDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class SetorService @Autowired
constructor(private val setorDAO: SetorDAO) {

    fun getList() = setorDAO.getList()
    fun getOrganograma() = setorDAO.getOrganograma()
    fun getSetoresDependentes(nome: String) = setorDAO.getSetoresDependentes(nome)
    fun getListUsuario(login: String) = setorDAO.getListUsuario(login)

    @Transactional(propagation = Propagation.REQUIRED)
    fun set(setor: Setor) {
        if (setor.id == null) {
            setor.id = getNextID()
        }
        setorDAO.set(setor)
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun getNextID() = setorDAO.getNextID()

    fun get(id: Int): Setor? {
        return setorDAO.get(id)
    }
}