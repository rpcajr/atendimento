package br.com.oxent.sfarma.model.entity

class Cliente(
        var id: Int? = null,
        val codCliente: String,
        val nome: String,
        val fantasia: String,
        val endereco: String? = null,
        val bairro: String? = null,
        val cidade: String? = null,
        val uf: String,
        val cep: String? = null,
        var fone: String? = null,
        var fone2: String? = null,
        val email: String? = null,
        val versao: String?,
        var contatos: List<Contato>? = emptyList()
)