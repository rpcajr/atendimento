package br.com.oxent.sfarma.model.entity

import java.time.LocalDateTime

class Chamado(
        var id: Int?,
        val versao: String?,
        val titulo: String?,
        val usuarioID: Int,
        var atribuicaoID: Int?,
        val ocorrenciaID: Int,
        val contatoID: Int?,
        val clienteID: Int?,
        val datAbertura: LocalDateTime,
        var datFechamento: LocalDateTime?,
        val detalhamento: String,
        val sistemas: String?,
        val palavaChave: String? = null,
        val prioridade: Int,
        val atendimento: String?,
        val situacao: Int? = 0,
        val solucao: String? = null,
        var anexos: List<JFFile> = mutableListOf(),
        var comentarios: List<Comentario> = mutableListOf()
)