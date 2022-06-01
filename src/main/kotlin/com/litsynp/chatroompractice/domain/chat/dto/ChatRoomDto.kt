package com.litsynp.chatroompractice.domain.chat.dto

import com.litsynp.chatroompractice.domain.chat.domain.ChatRoom
import org.springframework.stereotype.Component

class ChatRoomCreateRequestDto(val name: String)

class ChatRoomResponseDto(var id: Long?, val roomId: String)

@Component
class ChatRoomMapper {

    fun toResponseDto(entity: ChatRoom): ChatRoomResponseDto {
        return ChatRoomResponseDto(
            id = if (entity.id != null) entity.id else null,
            roomId = entity.roomId
        )
    }

    fun toEntity(dto: ChatRoomCreateRequestDto): ChatRoom {
        return ChatRoom(
            roomId = dto.name
        )
    }
}
