package br.com.oxent.sfarma.model.entity

import javax.validation.constraints.Size

class Setor(
        var id: Int?,
        @field:Size(max = 50, min = 1) val nome: String,
        var cor: String? = "",
        var paiID: Int,
        var nomePai: String? = ""
)