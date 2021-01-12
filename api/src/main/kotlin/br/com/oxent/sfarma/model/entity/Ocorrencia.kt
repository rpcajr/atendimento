package br.com.oxent.sfarma.model.entity

import javax.validation.constraints.Size

class Ocorrencia(
        var id: Int?,
        @field:Size(max = 30, min = 1) val nome: String,
        var setorID: Int,
        var setorNome: String? = ""
)