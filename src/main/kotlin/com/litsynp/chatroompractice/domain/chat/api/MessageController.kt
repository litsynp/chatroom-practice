package com.litsynp.chatroompractice.domain.chat.api

import com.litsynp.chatroompractice.domain.chat.domain.ChatRoom
import com.litsynp.chatroompractice.domain.chat.service.MessageService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class MessageController(
    messageService: MessageService
) {
    private val messageService: MessageService

    init {
        this.messageService = messageService
    }

    @PostMapping
    fun createRoom(@RequestParam name: String): ChatRoom {
        return messageService.createRoom(name)
    }

    @GetMapping
    fun findAllRoom(): List<ChatRoom> {
        return messageService.findAllRooms()
    }
}
