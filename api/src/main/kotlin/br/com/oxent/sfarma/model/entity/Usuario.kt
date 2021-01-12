 package br.com.oxent.sfarma.model.entity

import java.util.*

class Usuario(
        var id: Int?,
        val nome: String,
        val fone: String?,
        val email: String?,
        val nascimento: Date?,
        val login: String,
        val senha: String,
        val foto: String?,
        val url: String? = "",
        val inativo: Boolean,
        val permissoes: String = "",
        val setorID: Int
)