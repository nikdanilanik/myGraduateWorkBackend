package dev.vorstu.repositories;

import dev.vorstu.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    final Map<Long, User> students = null;
    Page<User> findByFioContainingIgnoreCase(String fullName, Pageable pageable);

    // для обновления последнего захода в сеть
    User findByFio(String fullName);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.wasTheLastTime = :wasTheLastTime WHERE u.id = :id")
    void updateWasTheLastTime(@Param("id") Long id, @Param("wasTheLastTime") Date wasTheLastTime);
}