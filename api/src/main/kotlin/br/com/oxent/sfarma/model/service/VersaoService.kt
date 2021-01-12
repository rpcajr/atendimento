package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.entity.Versao
import br.com.oxent.sfarma.model.repository.VersaoDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class VersaoService @Autowired
constructor(private val v: VersaoDAO) {

    fun getList() = v.getList()
    fun getTotal() = v.getTotal()

    @Transactional(propagation = Propagation.REQUIRED)
    fun set(versao: Versao) {
        v.set(versao)
    }

}