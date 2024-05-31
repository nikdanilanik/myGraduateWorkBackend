package dev.vorstu.repositories;

import dev.vorstu.entity.UserAccess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccessRepository extends CrudRepository<UserAccess, Long> {
    Optional<UserAccess> findByLogin(String login);
}
