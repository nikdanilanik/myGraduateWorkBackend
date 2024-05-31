package dev.vorstu.repositories;

import dev.vorstu.entity.User;
import dev.vorstu.entity.forMessage.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByUsers_id(Long userId);
    ChatRoom findByName(String chatName);
    @Query("SELECT cr FROM ChatRoom cr JOIN cr.users u WHERE u IN :users GROUP BY cr HAVING COUNT(DISTINCT u) = :userCount AND COUNT(DISTINCT u) = (SELECT COUNT(DISTINCT u2) FROM ChatRoom cr2 JOIN cr2.users u2 WHERE cr2.id = cr.id)")
    Optional<ChatRoom> findByUsersIn(@Param("users") List<User> users, @Param("userCount") Long userCount);
    List<ChatRoom> findByUsersContaining(User user);
}
