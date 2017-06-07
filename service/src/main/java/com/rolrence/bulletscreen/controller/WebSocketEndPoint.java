package com.rolrence.bulletscreen.controller;

import com.rolrence.bulletscreen.entity.User;
import com.rolrence.bulletscreen.service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by Rolrence on 2017/5/31.
 *
 */
public class WebSocketEndPoint extends TextWebSocketHandler {
    @Autowired
    private PythonService pythonService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User user = (User)session.getAttributes().get("user");
        if (user != null) {
            System.out.println("connection established, SessionId: " + PythonService.getSessionKey(session));
            session.sendMessage(new TextMessage("connection established."));
        } else {
            session.sendMessage(new TextMessage("authentication failed."));
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        pythonService.destroy(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String code = message.getPayload();
        pythonService.exec(session, code);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        System.out.println(exception.getMessage());
    }

}