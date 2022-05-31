package com.litsynp.chatroompractice.global.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry


@Configuration
@EnableWebSocket
class WebSocketConfig(
    webSocketHandler: MyWebSocketHandler
) : WebSocketConfigurer {

    private val webSocketHandler: MyWebSocketHandler

    init {
        this.webSocketHandler = webSocketHandler
    }

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*")
    }
}
