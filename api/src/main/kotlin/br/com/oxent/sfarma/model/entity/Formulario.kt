package br.com.oxent.sfarma.model.entity

import java.time.LocalDateTime

class Formulario(
        var id: Int?,
        val titulo: String?,
        val descricao: String? = null,
        var uuid: String,
        val nome: String? = null,
        val email: String? = null,
        val fone: String? = null,
        val empresa: String? = null,
        val cidade: String? = null,
        var formID: Int? = null,
        var questions: List<Question>? = mutableListOf(),
        val ativo: Boolean? = null,
        val dtCriacao: LocalDateTime? = null,
        val qtdResposta: Int? = null,
        var html: String? = null
)