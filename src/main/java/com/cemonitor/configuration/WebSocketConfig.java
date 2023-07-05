package com.cemonitor.configuration;

import com.cemonitor.websocket.DataWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Wojciech Dębski
 * @date 10/06/2023
 **/

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    private final DataWebSocketHandler dataWebSocketHandler;

    public WebSocketConfig(DataWebSocketHandler dataWebSocketHandler) {
        this.dataWebSocketHandler = dataWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(dataWebSocketHandler, "/websocket").setAllowedOrigins("*");
    }
}
