package dev.vorstu.repositories;

import dev.vorstu.entity.forMessage.ChatMessage;
import dev.vorstu.entity.forMessage.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // Для поиска последнего сообщения в чате
    Optional<ChatMessage> findTopByChatRoomOrderByIdDesc(ChatRoom chatRoom);

    @Query("SELECT m FROM ChatMessage m WHERE " +
            "(:chatRoomId IS NULL OR m.chatRoom.id = :chatRoomId) AND " +
            "(LOWER(m.content) LIKE LOWER(CONCAT('%', :searchText, '%')))")
    Page<ChatMessage> findByChatRoomIdAndContentContainingIgnoreCase(
            @Param("chatRoomId") Long chatRoomId,
            @Param("searchText") String searchText,
            Pageable pageable);
}
