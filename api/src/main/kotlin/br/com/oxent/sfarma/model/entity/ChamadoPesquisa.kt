package br.com.oxent.sfarma.model.entity

import java.time.LocalDateTime

class ChamadoPesquisa(
        val id: Int,
        val cliente: String?,
        val imagemUsuario: String,
        val nomeUsuario: String,
        val atendimento: String?,
        val titulo: String?,
        val situacao: Int,
        val ocorrencia: String,
        val prioridade: Int,
        val dataAbertura: LocalDateTime,
        val versao: String?,
        val contato: String?
)

