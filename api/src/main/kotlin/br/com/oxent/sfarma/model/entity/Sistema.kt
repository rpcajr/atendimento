package br.com.oxent.sfarma.model.entity

class Sistema(
        val seq: Int = 0,
        val nome: String = "",
        val limite: String = "",
        val contrato: String = "",
        val versao: String? = "",
        val datVersao: String = "",
        val grupo: Int = 0
)