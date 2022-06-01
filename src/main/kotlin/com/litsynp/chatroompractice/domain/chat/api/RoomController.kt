package com.litsynp.chatroompractice.domain.chat.api

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/chat")
class RoomController {

    @GetMapping("/rooms")
    fun room(model: Model): String {
        return "/chat/room-list"
    }

    @GetMapping("/rooms/{roomId}")
    fun roomEnter(@PathVariable roomId: String, model: Model): String {
        model.addAttribute("roomId", roomId)
        return "/chat/room-inside"
    }
}
