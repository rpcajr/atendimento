package br.com.oxent.sfarma.model.service

import br.com.oxent.sfarma.model.entity.Formulario
import br.com.oxent.sfarma.model.repository.FormularioDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.util.*
import java.util.function.Consumer
import javax.mail.MessagingException
import kotlin.concurrent.thread

@Service
@Transactional(readOnly = true)
class FormularioService @Autowired
constructor(private val dao: FormularioDAO,
            private val mailSender: JavaMailSender,
            private val thymeleaf: TemplateEngine) {

    fun get(uuid: String): Formulario? {
        val form = dao.get(uuid)
        form?.let {
            it.questions = dao.getQuest(it.id!!)
        }
        return form
    }

    fun listResposta(id: Int) = dao.listRespostas(id)

    @Transactional(propagation = Propagation.REQUIRED)
    fun delete(id: Int) = dao.delete(id)

    fun loadQuestions(formulario: Formulario): Formulario {
        formulario.questions = dao.getQuestRespostas(formulario.id!!)
        formulario.html = getHtml(formulario)
        return formulario
    }

    fun list(): List<Formulario> {
        return try {
            val form = dao.list()
            for (c in form) {
                c.questions = dao.getQuest(c.id!!)
            }
            form
        } catch (e: EmptyResultDataAccessException) {
            emptyList()
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun set(f: Formulario) {

        val update = f.id != null
        if (f.id == null || f.id == 0) {
            f.id = dao.getNextID()
        }

        if (f.uuid.isEmpty()) {
            f.uuid = UUID.randomUUID().toString()
        }

        dao.setForm(f)
        setQuestion(f, update)
    }


    @Transactional(propagation = Propagation.REQUIRED)
    fun setQuestion(f: Formulario, update: Boolean) {

        for (c in f.questions!!) {
            c.formID = f.id
            if (c.id == null) {
                c.id = dao.getNextIDQuest()
            }
            dao.setQuest(c)
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun responder(f: Formulario) {
        f.formID = f.id
        f.id = dao.getNextIDFormResp()
        dao.setFormResp(f)
        inserirRespostas(f)
        enviarEmail(f)
    }


    @Transactional(propagation = Propagation.REQUIRED)
    fun inserirRespostas(f: Formulario) {
        for (c in f.questions!!) {
            c.formID = f.id
            dao.setQuestResp(c)
        }
    }

    fun getHtml(formulario: Formulario): String {
        val context = Context(Locale("pt", "BR"))
        val variaveis: MutableMap<String, Any> = HashMap()
        variaveis["formulario"] = formulario
        variaveis.entries.forEach(
                Consumer<Map.Entry<String?, Any?>> { e: Map.Entry<String?, Any?> -> context.setVariable(e.key, e.value) })
        return thymeleaf.process("mail/formulario", context)
    }

    fun enviarEmail(formulario: Formulario) {
        thread {
            val email = dao.getEmailRespost(formulario.uuid)
            if (email.isBlank()) {
                return@thread
            }
            val mensagem = getHtml(formulario)
            val mimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, "UTF-8")
            helper.setFrom("inscricao@jfconsultores.com.br")
            helper.setTo(email)
            helper.setSubject(formulario.titulo!!)
            helper.setText(mensagem!!, true)
            mailSender.send(mimeMessage)
        }
    }

}