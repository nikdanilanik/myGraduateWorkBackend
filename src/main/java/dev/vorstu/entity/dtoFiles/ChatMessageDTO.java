package dev.vorstu.entity.dtoFiles;

import dev.vorstu.entity.forMessage.ChatMessage;
import dev.vorstu.entity.forMessage.ChatMessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private long id;
    private String content;
    private Date dateOfCreate;
    private ChatMessageStatus status;
    private Long userId;
    private Long chatRoomId;

    public ChatMessageDTO(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.content = chatMessage.getContent();
        this.dateOfCreate = chatMessage.getDateOfCreate();
        this.status = chatMessage.getStatus();
        this.userId = chatMessage.getUser() != null ? chatMessage.getUser().getId() : null;
        this.chatRoomId = chatMessage.getChatRoom() != null ? chatMessage.getChatRoom().getId() : null;
    }
}
