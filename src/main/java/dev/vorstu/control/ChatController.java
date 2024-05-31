package dev.vorstu.control;

import dev.vorstu.entity.User;
import dev.vorstu.entity.forMessage.ChatMessage;
import dev.vorstu.entity.forMessage.ChatRoom;
import dev.vorstu.repositories.ChatMessageRepository;
import dev.vorstu.repositories.ChatRoomRepository;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.Date;

@Controller
public class ChatController {
    String roomId = "";
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chat.sendMessage")
    @SendTo("/user/{roomId}/public")
    @Transactional
    public void sendMessage(@Payload ChatMessage chatMessage) {
        // Загружаем существующих пользователей и комнаты из базы данных
        User user = userRepository.findById(chatMessage.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getChatRoom().getId())
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        // Устанавливаем загруженные объекты
        chatMessage.setUser(user);
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setDateOfCreate(new Date());

        // Сохраняем сообщение
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        // Отправляем сообщение в конкретную комнату
        messagingTemplate.convertAndSend("/user/" + chatRoom.getId() + "/public", savedMessage);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("user", chatMessage.getUser());
        String roomId = String.valueOf(chatMessage.getChatRoom().getId());

        log.info(chatMessage.getUser().getFio());

        String destination = "/user/" + roomId + "/public";
        messagingTemplate.convertAndSend(destination, chatMessage);
    }
}
