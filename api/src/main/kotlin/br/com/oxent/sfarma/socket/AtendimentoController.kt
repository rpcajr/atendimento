package br.com.oxent.sfarma.socket

import br.com.oxent.sfarma.model.entity.UsuarioPerfil
import br.com.oxent.sfarma.model.repository.SetorDAO
import br.com.oxent.sfarma.model.service.SetorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller


@Controller
class AtendimentoController @Autowired
constructor(private val setorService: SetorService) {
    val map: MutableMap<String, MutableList<Atendimento>> = mutableMapOf()


    @MessageMapping("/add.perfil/{setor}")
    @SendTo("/topic/atendimento/{setor}")
    fun addAtendimento(@DestinationVariable setor: String, @Payload perfil: UsuarioPerfil, headerAccessor: SimpMessageHeaderAccessor): List<Atendimento> {
        val atendimento = Atendimento(sessionId = headerAccessor.messageHeaders["simpSessionId"].toString(), perfil = perfil)
        if (map[setor] == null) {
            map[setor] = mutableListOf(atendimento)
        } else {
            map[setor]!!.add(atendimento)
        }
        val list: MutableList<Atendimento> = mutableListOf()
        val setores = setorService.getSetoresDependentes(setor)
        for (s in setores) {
            if (map.containsKey(s.nome)) {
                list.addAll(map[s.nome]!!)
            }
        }
        return list
    }

    fun removeList(sessionId: String): Atendimento? {
        var atendimento: Atendimento? = null
        for ((_, v) in map) {
            atendimento = v.find { it.sessionId == sessionId }
            if (atendimento != null) {
                v.remove(atendimento)
                atendimento.remove = true
                break
            }
        }
        return atendimento
    }

    @MessageMapping("/call.atendimento/{setor}")
    @SendTo("/topic/atendimento/{setor}")
    fun atendimento(@DestinationVariable setor: String, @Payload atendimentos: List<AtendimentoCliente>, headerAccessor: SimpMessageHeaderAccessor): Atendimento? {
        val atendimento = map[setor]?.single { it.sessionId == headerAccessor.messageHeaders["simpSessionId"].toString() }
        atendimento?.list = atendimentos
        return atendimento
    }


}