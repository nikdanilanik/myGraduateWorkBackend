package dev.vorstu.control;

import dev.vorstu.components.Initializer;
import dev.vorstu.entity.User;
import dev.vorstu.repositories.UserAccessRepository;
import dev.vorstu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/base")
public class BaseController {

    private Long counter = 0L;
    private Long generateId() { return counter++; }
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccessRepository userAccessRepository;

    @GetMapping("users")
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(defaultValue = "") String filter) {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, direction, sort[0]);
        if (filter != null && !filter.trim().isEmpty()) {
            return userRepository.findByFioContainingIgnoreCase(filter, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Autowired
    private Initializer initializer;

    @PostMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    private User addUser(User user) {
        user.setId(generateId());
        return userRepository.save(user);
    }

    @DeleteMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteUser(@PathVariable("id") Long id) {
        return removeUser(id);
    }

    private Long removeUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    @GetMapping(value = "users/fio/{fio}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserByFio(@PathVariable("fio") String fio) {
        return userRepository.findByFio(fio);
    }

    @GetMapping(value = "users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserbyId(@PathVariable("id") Long id) {
        return getAllUsers().stream()
                .filter(el -> el.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("user with id: " + id + " was not found"));
    }

    @PutMapping(value = "users/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public User changeUser(@RequestBody User changingUser) {
        return updateUser(changingUser);
    }

    private User updateUser(User user) {
        if(user.getId() == null) {
            throw new RuntimeException("id of changing user cannot be null");
        }

//        User changingUser = getAllUsers().stream()
//                .filter(el -> Objects.equals(el.getId(), user.getId()))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("user with id: " + user.getId() + "was not found"));

        Optional<User> changingUserOptional = userRepository.findById(user.getId());
        if (changingUserOptional.isPresent()) {
            User changingUser = changingUserOptional.get();changingUser.setFio(user.getFio());
            changingUser.setInfo(user.getInfo());
            changingUser.setDateOfRegistr(user.getDateOfRegistr());
            changingUser.setWasTheLastTime(user.getWasTheLastTime());
            changingUser.setAvatar(user.getAvatar());
            return userRepository.save(changingUser);
        } else {
            throw new RuntimeException("user with id: " + user.getId() + "was not found");
        }
    }

    @PutMapping(value = "users/updateForUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public User changeUserForUser(@RequestBody User changingUser) {
        return updateUserForUsers(changingUser);
    }
    private User updateUserForUsers(User user) {
        if(user.getId() == null) {
            throw new RuntimeException("id of changing user cannot be null");
        }

        User changingUser = getAllUsers().stream()
                .filter(el -> Objects.equals(el.getId(), user.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("user with id: " + user.getId() + "was not found"));

        changingUser.setInfo(user.getInfo());
        changingUser.setAvatar(user.getAvatar());
        return userRepository.save(changingUser);
    }

    // загрузка файла
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        // Логика сохранения файла
        try {
            // Сохраните файл на сервере
            String fileName = file.getOriginalFilename();
            Path path = Paths.get("uploads/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}
