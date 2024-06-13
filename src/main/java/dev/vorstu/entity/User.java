package dev.vorstu.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "fio")
    private String fio;
    @Column(name = "info")
    private String info;
    @Column (name = "dateOfRegistr")
    private Date dateOfRegistr;
    @Column (name = "wasTheLastTime")
    private Date wasTheLastTime;
    @Column(name = "avatar")
    private String avatar;

    public User(String fio, String info, Date dateOfRegistr, Date wasTheLastTime, String avatar) {
        this.fio = fio;
        this.info = info;
        this.dateOfRegistr = dateOfRegistr;
        this.wasTheLastTime = wasTheLastTime;
        this.avatar = avatar;
    }
}