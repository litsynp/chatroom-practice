package com.litsynp.chatroompractice.global.websocket

import com.litsynp.chatroompractice.global.util.logger
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler


@Component
class MyWebSocketHandler : TextWebSocketHandler() {

    private val log = logger()

    @Throws(Exception::class)
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        log.info("payload : {}", payload)

        val initialGreeting = TextMessage("Welcome to the chat server.")
        session.sendMessage(initialGreeting)
    }
}
