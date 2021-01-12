package br.com.oxent.sfarma.model.entity

import java.util.*

class Contato(
        var id: Int?,
        var clienteID: Int?,
        val nome: String,
        val nascimento: Date?,
        val email: String?,
        val fone: String?,
        val fone2: String?,
        val cargo: String?
)