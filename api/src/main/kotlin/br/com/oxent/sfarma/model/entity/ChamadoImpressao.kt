package br.com.oxent.sfarma.model.entity

import java.util.*

class ChamadoImpressao(
        val codCliente: String,
        val nomeCliente: String,
        val cidade: String,
        val versao: String?,
        val datAbertura: Date,
        val datFechamento: Date?,
        val titulo: String?,
        val detalhamento: String?,
        val solucao: String?,
        val contato: String?,
        val suporte: String,
        val sistema: String?,
        val ocorrencia: String?,
        val situacao: Int,
        val prioridade: Int
)