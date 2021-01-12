package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.entity.Chamado
import br.com.oxent.sfarma.model.entity.ChamadoImpressao
import br.com.oxent.sfarma.model.entity.Filter
import br.com.oxent.sfarma.model.entity.JFFile
import br.com.oxent.sfarma.model.repository.ChamadoDAO
import br.com.oxent.sfarma.resource.handler.JFException
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.InputStream
import java.time.LocalDateTime
import java.util.HashMap
import java.util.Locale


@Service
@Transactional(readOnly = true)
class ChamadoService @Autowired
constructor(private val dao: ChamadoDAO, private val clienteService: ClienteService) {

    @Transactional(propagation = Propagation.REQUIRED)
    @ExceptionHandler(JFException::class)
    fun set(c: Chamado) {
        if (c.id == null) {
            c.id = getNextID()
        }
        if (c.atribuicaoID == null) {
            c.atribuicaoID = c.usuarioID
        }
        if (c.situacao == 0) {
            c.datFechamento = LocalDateTime.now()
        }
        dao.set(c)
        if (c.clienteID != null && c.versao != null) {
            clienteService.atualizaVersao(c.clienteID, c.versao)
        }
        inserirAnexos(c.anexos, c.id!!)
        inserirComentario(c)
    }

    fun list(f: Filter?, p: Pageable, name: String) = dao.list(f, p, name)

    @Transactional(propagation = Propagation.REQUIRED)
    fun delete(id: Int) {
        dao.deleteAnexo(id)
        dao.deleteComentario(id)
        dao.deleteChamado(id)
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun getNextID() = dao.getNextID()

    fun get(id: Int): Chamado? {
        val chamado = dao.get(id)
        try {
            chamado?.anexos = dao.getAnexos(id)
        } catch (e: EmptyResultDataAccessException) {
        }

        try {
            chamado?.comentarios = dao.getComentarios(id)
        } catch (e: EmptyResultDataAccessException) {
        }

        return chamado
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun inserirComentario(chamado: Chamado) {
        dao.deleteComentario(chamado.id!!)
        for (c in chamado.comentarios) {
            c.chamadoId = chamado.id
            dao.inserirComentario(c)
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun inserirAnexos(anexos: List<JFFile>, chamadoID: Int) {
        dao.deleteAnexo(chamadoID)
        for (f in anexos) {
            dao.inserirAnexo(f, chamadoID)
        }
    }

    @Throws(Exception::class)
    fun relatorio(filter: Filter?, name: String): ByteArray? {
        val dados: List<ChamadoImpressao?> = dao.getChamados(filter, name)
        val parametros: MutableMap<String, Any> = HashMap()
        parametros["REPORT_LOCALE"] = Locale("pt", "BR")
        val inputStream: InputStream = this.javaClass.getResourceAsStream("/relatorios/Atendimento.jasper")
        val jasperPrint: JasperPrint = JasperFillManager.fillReport(inputStream, parametros, JRBeanCollectionDataSource(dados))
        return JasperExportManager.exportReportToPdf(jasperPrint)
    }



}