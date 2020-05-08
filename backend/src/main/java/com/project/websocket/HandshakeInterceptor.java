package com.project.websocket;

import com.project.service.UserService;
import com.project.util.CONSTANT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;


public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes)
            throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                String username = (String) session.getAttribute(CONSTANT.DEFAULT_SESSION_USERNAME);
                if (username == null)
                    username = "system";
                attributes.put(CONSTANT.DEFAULT_WEBSOCKET_USERNAME, username);
                return true;
            }
            else {
                String username = servletRequest.getServletRequest().getParameter("username");
                String password = servletRequest.getServletRequest().getParameter("password");
                if(((UserService)ContextLoader.getCurrentWebApplicationContext().getBean("userServiceImpl")).loginCheck(username, password) == 1) {
                    attributes.put(CONSTANT.DEFAULT_WEBSOCKET_USERNAME, username);
                    return true;
                }
                else return false;
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

}
