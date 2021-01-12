package br.com.oxent.sfarma.model.entity

class Message {
    var type: MessageType? = null
    var content: String? = null
    var sender: String? = null

    enum class MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}