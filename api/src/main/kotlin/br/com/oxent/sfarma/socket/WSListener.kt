package br.com.oxent.sfarma.socket

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WSListener {
    private val logger = LoggerFactory.getLogger(WSListener::class.java)

    @Autowired
    private val messagingTemplate: SimpMessageSendingOperations? = null
    @Autowired
    private val controller: AtendimentoController? = null

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectedEvent) {

    }

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val headerAccessor = StompHeaderAccessor.wrap(event.message)
        val session = headerAccessor.messageHeaders["simpSessionId"].toString()
        controller?.removeList(session)?.let { messagingTemplate!!.convertAndSend("/topic/atendimento/${it.perfil.setor}", it) }
    }
}