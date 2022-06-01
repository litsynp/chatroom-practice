package com.litsynp.chatroompractice.domain.chat.api

import com.litsynp.chatroompractice.domain.chat.domain.ChatMessage
import com.litsynp.chatroompractice.domain.chat.domain.ChatMessageType
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageWsController(
    val sendingOperations: SimpMessageSendingOperations
) {
    @MessageMapping("/comm/message")
    fun message(message: ChatMessage) {
        if (ChatMessageType.ENTER == message.type) {
            message.message = "\"${message.sender}\"님이(가) 입장했습니다."
        }

        sendingOperations.convertAndSend("/sub/comm/room/${message.roomId}", message)
    }
}
