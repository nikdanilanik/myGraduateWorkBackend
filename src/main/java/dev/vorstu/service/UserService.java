package dev.vorstu.service;

import dev.vorstu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updateWasTheLastTime(Long userId) {
        Date currentTime = new Date();
        userRepository.updateWasTheLastTime(userId, currentTime);
    }
}
