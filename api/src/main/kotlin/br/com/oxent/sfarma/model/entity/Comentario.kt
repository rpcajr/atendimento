package br.com.oxent.sfarma.model.entity

import java.time.LocalDateTime

class Comentario(
        var chamadoId: Int? = 0,
        val usuario: UsuarioPerfil,
        val comentario: String,
        val datComentario: LocalDateTime
)