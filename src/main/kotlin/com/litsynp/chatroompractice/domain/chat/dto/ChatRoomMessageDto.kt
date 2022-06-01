package com.litsynp.chatroompractice.domain.chat.dto

import com.litsynp.chatroompractice.domain.chat.domain.ChatMessage
import com.litsynp.chatroompractice.domain.chat.domain.ChatMessageType
import org.springframework.stereotype.Component

data class ChatRoomMessageResponseDto(
    val type: ChatMessageType,
    val roomId: String,
    val sender: String,
    val message: String
)

@Component
class ChatMessageMapper {

    fun toResponseDto(entity: ChatMessage): ChatRoomMessageResponseDto {
        return ChatRoomMessageResponseDto(
            type = entity.type,
            message = entity.message,
            roomId = entity.roomId,
            sender = entity.sender
        )
    }
}
