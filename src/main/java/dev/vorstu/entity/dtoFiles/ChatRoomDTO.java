package dev.vorstu.entity.dtoFiles;

import dev.vorstu.entity.forMessage.ChatMessage;
import dev.vorstu.entity.forMessage.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {
    private Long id;
    private String name;
    private List<UserDTO> users;
    private ChatMessageDTO lastMessage;

    public ChatRoomDTO(ChatRoom chatRoom, ChatMessage lastMessage) {
        this.id = chatRoom.getId();
        this.name = chatRoom.getName();
        this.users = chatRoom.getUsers().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
        this.lastMessage = lastMessage != null ? new ChatMessageDTO(lastMessage) : null;
    }
}