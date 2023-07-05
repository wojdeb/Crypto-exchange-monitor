package com.cemonitor.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author Wojciech Dębski
 * @date 10/06/2023
 **/

@Service
public class DataWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionManager sessionManager;

    public DataWebSocketHandler(WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionManager.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionManager.removeSession(session);
    }

    public void sendDataToClients(String jsonData) {
        try {
            sessionManager.sendToAllSessions(new TextMessage(jsonData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
