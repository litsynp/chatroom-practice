package com.litsynp.chatroompractice.domain.chat.domain

class ChatMessage(
    val type: ChatMessageType,
    val roomId: String,
    val sender: String,
    var message: String
)

enum class ChatMessageType {
    ENTER, COMM
}
