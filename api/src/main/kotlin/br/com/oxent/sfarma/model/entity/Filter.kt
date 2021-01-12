package br.com.oxent.sfarma.model.entity

import java.util.*

class Filter(
        val clienteID: Int? = null,
        val datInicial: Date? = null,
        val datFinal: Date? = null,
        val ocorrenciaID: Int? = null,
        val setorID: List<Int>? = null,
        val versao: String? = null,
        val situacao: String = "",
        val sistema: String? = null,
        val atribuidoID: Int? = null
)