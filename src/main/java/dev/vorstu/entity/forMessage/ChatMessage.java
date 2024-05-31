package dev.vorstu.entity.forMessage;

import dev.vorstu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chatMessage")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "content")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
    @Column(name = "timestamp")
    private Date dateOfCreate;
    @Column(name = "status")
    @Enumerated (EnumType.STRING)
    private ChatMessageStatus status;

    public ChatMessage(User user, String content, ChatRoom chatRoom, Date dateOfCreate, ChatMessageStatus status) {
        this.user = user;
        this.content = content;
        this.chatRoom = chatRoom;
        this.dateOfCreate = dateOfCreate;
        this.status = status;
    }
}
