package com.cemonitor.websocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wojciech Dębski
 * @date 10/06/2023
 **/

@Service
public class WebSocketSessionManager {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    public synchronized void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public synchronized void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public synchronized void sendToAllSessions(TextMessage message) {
        List<WebSocketSession> closedSessions = new ArrayList<>();
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                closedSessions.add(session);
            }
        }
        sessions.removeAll(closedSessions);
    }
}
