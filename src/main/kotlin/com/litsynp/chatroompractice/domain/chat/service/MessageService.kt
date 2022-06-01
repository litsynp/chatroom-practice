package com.litsynp.chatroompractice.domain.chat.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.litsynp.chatroompractice.domain.chat.domain.ChatRoom
import com.litsynp.chatroompractice.global.util.logger
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.io.IOException

@Service
class MessageService {

    private val log = logger()
    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())
    private val chatRooms: MutableMap<String, ChatRoom> = mutableMapOf()

    fun findAll(): List<ChatRoom> {
        return chatRooms.values.toList()
    }

    fun findById(id: String): ChatRoom? {
        return chatRooms[id]
    }

    fun create(entity: ChatRoom): ChatRoom {
        chatRooms[entity.roomId] = entity
        return entity
    }

    fun <T> sendMessage(session: WebSocketSession, message: T) {
        try {
            log.info("Trying sendMessage")
            session.sendMessage(TextMessage(objectMapper.writeValueAsString(message)))
        } catch (e: IOException) {
            log.error(e.message, e)
        }
    }
}
