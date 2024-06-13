package dev.vorstu.service;

import dev.vorstu.components.WebSocketEventListener;
import dev.vorstu.entity.User;
import dev.vorstu.entity.forMessage.ChatMessage;
import dev.vorstu.entity.dtoFiles.ChatMessageDTO;
import dev.vorstu.entity.forMessage.ChatRoom;
import dev.vorstu.repositories.ChatMessageRepository;
import dev.vorstu.repositories.ChatRoomRepository;
import dev.vorstu.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatRoom addChatRoom(ChatRoom chatRoom) {
        if (chatRoom != null) {
            return chatRoomRepository.save(chatRoom);
        } else return null;
    }
    public ChatRoom addUserToChatRoom(Long chatId, Long userId) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatId);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                chatRoom.getUsers().add(user);
                return chatRoomRepository.save(chatRoom);
            } else {
                throw new IllegalArgumentException("User with id " + userId + " not found.");
            }
        } else {
            throw new IllegalArgumentException("Chat room with id " + chatId + " not found.");
        }
    }
    public void deleteChatRoom(Long id) {
        chatRoomRepository.deleteById(id);
    }
    public ChatRoom findChatRoomById(Long chatId) {
        return chatRoomRepository.findById(chatId).orElse(null);
    }
    public List<ChatRoom> findChatRoomsByUserId(Long userId) {
        return chatRoomRepository.findByUsers_id(userId);
    }
    public List<ChatRoom> findChatRoomByUsersId(Set<Long> userIds) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();

        return chatRooms.stream()
                .filter(chatRoom -> chatRoom.getUsers().stream()
                        .map(User::getId)
                        .collect(Collectors.toSet())
                        .containsAll(userIds))
                .collect(Collectors.toList());
    }
    public ChatRoom findByName(String chatName) {
        return chatRoomRepository.findByName(chatName);
    }
    public ChatRoom findOrCreateChatRoomByUsers(ChatRoom chatRoom) {
        List<User> users = chatRoom.getUsers();
        Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByUsersIn(users, (long) users.size());
        return optionalChatRoom.orElseGet(() -> {
            ChatRoom newChatRoom = new ChatRoom(chatRoom.getName(), users);
            return chatRoomRepository.save(newChatRoom);
        });
    }
    public Page<ChatMessageDTO> searchMessages(Long chatRoomId, String searchText, Pageable pageable) {
        Page<ChatMessage> messages = chatMessageRepository.findByChatRoomIdAndContentContainingIgnoreCase(chatRoomId, searchText, pageable);
        List<ChatMessageDTO> dtos = messages.getContent().stream()
                .map(chatMessage -> new ChatMessageDTO(chatMessage))
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, messages.getTotalElements());
    }
}
