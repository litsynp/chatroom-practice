package com.litsynp.chatroompractice.global.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.litsynp.chatroompractice.domain.chat.domain.ChatMessage
import com.litsynp.chatroompractice.domain.chat.service.MessageService
import com.litsynp.chatroompractice.global.util.logger
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler


@Component
class MyWebSocketHandler(messageService: MessageService) : TextWebSocketHandler() {

    private val log = logger()
    private val messageService: MessageService
    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    init {
        this.messageService = messageService
    }

    @Throws(Exception::class)
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        log.info("payload : {}", payload)

        val msg = objectMapper.readValue(payload, ChatMessage::class.java)
        val room = messageService.findById(msg.roomId)
        room?.handleActions(session, msg, messageService)
    }
}
