package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.entity.Ocorrencia
import br.com.oxent.sfarma.model.repository.OcorrenciaDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class OcorrenciaService @Autowired
constructor(private val ocorrenciaDAO: OcorrenciaDAO) {

    fun getList() = ocorrenciaDAO.getList()
    fun getListOcorrenciaDoSetor(name: String) = ocorrenciaDAO.getOcorrenciaDoSetor(name)

    fun getTotal() = ocorrenciaDAO.getTotal()

    @Transactional(propagation = Propagation.REQUIRED)
    fun set(ocorrencia: Ocorrencia) {
        if (ocorrencia.id == null) {
            ocorrencia.id = getNextID()
        }
        ocorrenciaDAO.set(ocorrencia)
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun getNextID() = ocorrenciaDAO.getNextID()
}