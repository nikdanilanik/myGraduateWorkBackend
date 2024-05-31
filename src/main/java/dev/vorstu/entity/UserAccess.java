package dev.vorstu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "usersAccess")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Long userId;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "passwords_id", nullable = false)
    private Password password;

    private boolean enable;
}
