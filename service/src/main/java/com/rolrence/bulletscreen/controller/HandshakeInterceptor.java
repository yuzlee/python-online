package com.rolrence.bulletscreen.controller;

import java.util.Map;

import com.rolrence.bulletscreen.entity.User;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;

/**
 * Created by Rolrence on 2017/5/31.
 *
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        // System.out.println("Before Handshake");
        if(request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
            HttpSession httpSession = servletRequest.getServletRequest().getSession(true);
            if(null != httpSession){
                User user = (User)httpSession.getAttribute("user");
                attributes.put("user", user);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        // System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}