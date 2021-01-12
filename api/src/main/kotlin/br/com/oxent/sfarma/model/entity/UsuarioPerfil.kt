package br.com.oxent.sfarma.model.entity

class UsuarioPerfil(
        val id: Int,
        val nome: String,
        val setor: String,
        val foto: String?,
        var url: String? = ""
)