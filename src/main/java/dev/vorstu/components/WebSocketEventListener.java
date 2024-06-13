package dev.vorstu.components;

import dev.vorstu.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    // Подключение к WebSocket
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    // Отключение от WebSocket
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        User username = (User) headerAccessor.getSessionAttributes().get("user");
        if(username != null) {
            logger.info("User Disconnected : " + username.getFio());
//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setType(ChatMessage.MessageType.LEAVE);
//            chatMessage.setSenderId(username);
            String roomId = "16";
            String destination = "/user/" + roomId + "/public";
            messagingTemplate.convertAndSend(destination, username);
        }
    }
}
