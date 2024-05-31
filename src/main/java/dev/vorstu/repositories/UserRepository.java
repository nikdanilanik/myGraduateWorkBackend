package dev.vorstu.repositories;

import dev.vorstu.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    final Map<Long, User> students = null;
    Page<User> findByFioContainingIgnoreCase(String fullName, Pageable pageable);
    User findByFio(String fullName);
}