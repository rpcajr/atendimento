package br.com.oxent.sfarma.model.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class ClienteDTOService @Autowired
constructor(private val dao: JFDadosDAO) {
    fun getCliente(cod: Int) = dao.getCliente(cod)
    fun getList() = dao.getList()
    fun getCount() = dao.getCountClientesSemBackup()
    fun update(cod: Int) = dao.update(cod)
    fun getListClientesVersoes() = dao.getListClientesVersoes()
}