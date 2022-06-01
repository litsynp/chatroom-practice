package com.litsynp.chatroompractice.domain.chat.api

import com.litsynp.chatroompractice.domain.chat.domain.ChatRoom
import com.litsynp.chatroompractice.domain.chat.dto.ChatRoomCreateRequestDto
import com.litsynp.chatroompractice.domain.chat.dto.ChatRoomMapper
import com.litsynp.chatroompractice.domain.chat.dto.ChatRoomResponseDto
import com.litsynp.chatroompractice.domain.chat.service.MessageService
import com.litsynp.chatroompractice.global.errors.ResourceNotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class RoomApiController(
    private val messageService: MessageService,
    private val mapper: ChatRoomMapper
) {
    @GetMapping("/rooms")
    fun list(): List<ChatRoomResponseDto> {
        return messageService.findAll().map(mapper::toResponseDto)
    }

    @GetMapping("/rooms/{roomId}")
    fun get(@PathVariable roomId: String): ChatRoomResponseDto {
        val entity = messageService.findById(roomId) ?: throw ResourceNotFoundException()
        return mapper.toResponseDto(entity)
    }

    @PostMapping("/rooms")
    fun create(@RequestBody dto: ChatRoomCreateRequestDto): ChatRoom {
        return messageService.create(mapper.toEntity(dto))
    }
}
