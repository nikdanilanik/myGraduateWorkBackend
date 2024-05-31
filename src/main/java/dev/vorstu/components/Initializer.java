package dev.vorstu.components;

import dev.vorstu.entity.Password;
import dev.vorstu.entity.Role;
import dev.vorstu.entity.User;
import dev.vorstu.entity.UserAccess;
import dev.vorstu.entity.forMessage.ChatMessage;
import dev.vorstu.entity.forMessage.ChatMessageStatus;
import dev.vorstu.entity.forMessage.ChatRoom;
import dev.vorstu.repositories.ChatMessageRepository;
import dev.vorstu.repositories.ChatRoomRepository;
import dev.vorstu.repositories.UserAccessRepository;
import dev.vorstu.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class Initializer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Transactional
    public void initial() {
        // Генерация базовых пользователей
        User user1 = userRepository.saveAndFlush(new User("No Name", "Нету инфы", new Date(), new Date(1), "https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        User user2 = userRepository.saveAndFlush(new User("Дед Инсайд", "Нету инфы", new Date(), new Date(1), "https://avavatar.ru/images/full/19/mrBXqhnN32kQnYeK.jpg"));
        userRepository.saveAndFlush(new User("Какаши Хатаке", "Нету инфы", new Date(), new Date(1), "https://pixelbox.ru/wp-content/uploads/2021/09/cool-avatar-tik-tok-3.jpeg"));
        userRepository.save(new User("Владимир Владимирович", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/45/45b865d134d2c25cf5ed131317fe88de.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));
        userRepository.save(new User("No Name", "Нету инфы", new Date(), new Date(1),"https://static10.tgstat.ru/channels/_0/64/6471e19cd8c9bbe4fbbd4ec33bf3d1f0.jpg"));

        // Генерация доступа для пользователей
        userAccessRepository.save(new UserAccess(null, "user", Role.USER, 1L, new Password("1234"), true));
        userAccessRepository.save(new UserAccess(null, "user1", Role.USER, 2L, new Password("1234"), true));
        userAccessRepository.save(new UserAccess(null, "user2", Role.USER, 3L, new Password("1234"), true));
        userAccessRepository.save(new UserAccess(null, "user3", Role.USER, 3L, new Password("1234"), true));

        // Генерация чатов
        List<User> usersForChatRooms = new ArrayList<>();
        usersForChatRooms.add(user1);
        usersForChatRooms.add(user2);

        ChatRoom chatRoom1_2 = new ChatRoom("1_2", usersForChatRooms);
        chatRoomRepository.save(chatRoom1_2);

        // Генерация сообщений пользователей
        chatMessageRepository.save(new ChatMessage(user1, "Привет", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Привет!", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "Как твои дела?", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Отлично!", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "А твои как?", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "Да тоже всё хорошо", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "Чем занимаешься?", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Да ничем интересным особо не занимаюсь", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Погулять думаю вот выйти", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "А ты что делаешь?", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Не хочешь выйти погулять?", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "Да я пока немного занят", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "Помогаю тут по дому", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "То пропылесосить, то с покушать приготовить помочь", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "А погулять...", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "Наверное, если я и смогу, то ближе к вечеру", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Отлично!", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Я как раз планировал до ночи гулять!", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Тогда напишешь, как освободишься, хорошо?", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "Да, хорошо", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user1, "Спишемся тогда", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
        chatMessageRepository.save(new ChatMessage(user2, "Да, спишемся", chatRoom1_2, new Date(), ChatMessageStatus.SENT));
    }
    public User SaveUser(User user) {
        return userRepository.save(user);
    }
}
