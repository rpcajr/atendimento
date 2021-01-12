package br.com.oxent.sfarma.model.entity

class Question(
        var id: Int?,
        var formID:Int?,
        val titulo: String,
        val valor: String? = null,
        val tipo: Int,
        val opcoes: List<String>,
        val obrigatorio: Boolean
)