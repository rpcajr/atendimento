package br.com.oxent.sfarma.model.entity

import java.util.*

class ClienteDTO(
        val codCliente: String,
        val datBackup: Date?,
        val dias: Int,
        val nome: String? = null,
        val cidade: String? = null
)