package dev.vorstu.entity.dtoFiles;

import dev.vorstu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String fio;
    private String info;
    private Date dateOfRegistr;
    private Date wasTheLastTime;
    private String avatar;

    public UserDTO(User user) {
        this.id = user.getId();
        this.fio = user.getFio();
        this.info = user.getInfo();
        this.dateOfRegistr = user.getDateOfRegistr();
        this.wasTheLastTime = user.getWasTheLastTime();
        this.avatar = user.getAvatar();
    }
}
