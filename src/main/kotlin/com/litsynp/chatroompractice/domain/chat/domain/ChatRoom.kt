package com.litsynp.chatroompractice.domain.chat.domain

import com.litsynp.chatroompractice.domain.chat.service.MessageService
import org.springframework.web.socket.WebSocketSession

class ChatRoom(
    val roomId: String
) {
    private val sessions: MutableSet<WebSocketSession> = mutableSetOf()

    fun handleActions(session: WebSocketSession, message: ChatMessage, msgService: MessageService) {
        if (message.type.equals(ChatMessageType.ENTER)) {
            sessions.add(session)
            message.message = message.sender + "님이 입장했습니다."
        }
        sendMessage<Any>(message, msgService)
    }

    private fun <T> sendMessage(message: T, messageService: MessageService) {
        sessions.parallelStream().forEach { session: WebSocketSession? ->
            messageService.sendMessage(
                session!!,
                message
            )
        }
    }
}
