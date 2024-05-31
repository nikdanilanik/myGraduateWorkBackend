package dev.vorstu.control;

import dev.vorstu.entity.User;
import dev.vorstu.entity.forMessage.ChatMessageDTO;
import dev.vorstu.entity.forMessage.ChatRoom;
import dev.vorstu.repositories.ChatRoomRepository;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/chatController")
public class ChatRestController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    // Поиск одно чата, где ChatRoom.users = result_ChatRoom.User, и даже их количество
    @PostMapping(value = "chatRoom", produces = MediaType.APPLICATION_JSON_VALUE)
    public ChatRoom findOrCreateChatById(@RequestBody ChatRoom chatRoom) {
        return chatService.findOrCreateChatRoomByUsers(chatRoom);
    }

    // Поиск всех чатов по user.id
    @GetMapping("chatRooms")
    public List<ChatRoom> getUserChatRooms(@RequestParam Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return chatRoomRepository.findByUsersContaining(user);
    }

    // Поиск сообщений по чату, пользователю + фильтрация с пагинацией
    @GetMapping("messages/search")
    public Page<ChatMessageDTO> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(required = false) Long chatRoomId,
            @RequestParam(required = false, defaultValue = "") String searchText) {

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, direction, sort[0]);

        return chatService.searchMessages(chatRoomId, searchText, pageable);
    }
}
