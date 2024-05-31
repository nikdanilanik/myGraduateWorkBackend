package dev.vorstu.entity.forMessage;

import dev.vorstu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chatRoom")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<User> users;

    public ChatRoom(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }
}
