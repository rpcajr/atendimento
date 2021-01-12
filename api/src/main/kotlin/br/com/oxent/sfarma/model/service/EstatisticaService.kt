package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.repository.EstatisticaDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class EstatisticaService @Autowired
constructor(private val dao: EstatisticaDAO) {

    fun getListVersao() = dao.getListVersao()

}