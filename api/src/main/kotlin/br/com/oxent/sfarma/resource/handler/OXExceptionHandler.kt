package br.com.oxent.sfarma.resource.handler

import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.ArrayList


@ControllerAdvice
class OXExceptionHandler @Autowired
constructor(private val messageSource: MessageSource) : ResponseEntityExceptionHandler() {

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException,
                                              headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        val mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale())
        val mensagemDesenvolvedor = if (ex.cause != null) ex.cause.toString() else ex.toString()
        val erros = listOf(Erro(mensagemUsuario, mensagemDesenvolvedor))
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        val erros = criarListaDeErros(ex.bindingResult)
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(JFException::class)
    fun jfException(ex: JFException, request: WebRequest): ResponseEntity<Any> {
        val mensagemUsuario = ex.message!!
        val mensagemDesenvolvedor = ex.toString()
        val erros = listOf(Erro(mensagemUsuario, mensagemDesenvolvedor))
        return handleExceptionInternal(ex, erros, HttpHeaders(), HttpStatus.CONFLICT, request)
    }


    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(ex: EmptyResultDataAccessException, request: WebRequest): ResponseEntity<Any> {
        val mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale())
        val mensagemDesenvolvedor = ex.toString()
        val erros = listOf(Erro(mensagemUsuario, mensagemDesenvolvedor))
        return handleExceptionInternal(ex, erros, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException, request: WebRequest): ResponseEntity<Any> {
        val mensagemUsuario = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale())
        val mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex)
        val erros = listOf(Erro(mensagemUsuario, mensagemDesenvolvedor))
        return handleExceptionInternal(ex, erros, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    private fun criarListaDeErros(bindingResult: BindingResult): List<Erro> {
        val erros = ArrayList<Erro>()

        for (fieldError in bindingResult.fieldErrors) {
            val mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())
            val mensagemDesenvolvedor = fieldError.toString()
            erros.add(Erro(mensagemUsuario = mensagemUsuario,
                            mensagemDesenvolvedor = mensagemDesenvolvedor))
        }

        return erros
    }

    class Erro(val mensagemUsuario: String,
               val mensagemDesenvolvedor: String)

}
