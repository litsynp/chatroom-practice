package com.litsynp.chatroompractice.domain.chat.dao

import com.litsynp.chatroompractice.domain.chat.domain.ChatRoom
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
class ChatRoomRepository {

    private lateinit var chatRoomMap: MutableMap<String, ChatRoom>

    @PostConstruct
    private fun init() {
        chatRoomMap = LinkedHashMap()
    }

    fun findAll(): List<ChatRoom> {
        val chatRooms = ArrayList(chatRoomMap.values)
        chatRooms.reverse()
        return chatRooms
    }

    fun findOneByRoomId(roomId: String): ChatRoom? {
        return chatRoomMap[roomId]
    }

    fun create(name: String): ChatRoom {
        val room = ChatRoom(roomId = name)
        chatRoomMap[room.roomId] = room
        return room
    }
}
