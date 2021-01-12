package br.com.oxent.sfarma.socket

import br.com.oxent.sfarma.model.entity.UsuarioPerfil

class Atendimento(
        val sessionId: String,
        val perfil: UsuarioPerfil,
        var list: List<AtendimentoCliente>? = emptyList(),
        var remove: Boolean = false

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Atendimento

        if (sessionId != other.sessionId) return false

        return true
    }

    override fun hashCode(): Int {
        return sessionId.hashCode()
    }
}