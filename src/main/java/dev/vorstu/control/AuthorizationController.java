package dev.vorstu.control;

import dev.vorstu.entity.*;
import dev.vorstu.repositories.UserAccessRepository;
import dev.vorstu.repositories.UserRepository;
import dev.vorstu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/authAndRegistr")
public class AuthorizationController {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccessRepository userAccessRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public Principal apiLogin(Principal user) {
        log.info("Login user");
        UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) user);
        log.info("Hell {} with role {}", token.getName(), token.getAuthorities());

        // Можно сделать через jdbc Репозиторий
        String username = token.getName();
        String userIdQuery = "SELECT user_id FROM users_access WHERE login = ?";
        Long userId = jdbcTemplate.queryForObject(userIdQuery, new Object[] { username }, Long.class);

        // Для обновления времени захода
        User tempUser = new User();
        userService.updateWasTheLastTime(userId);

        Map<String, Object> details = new HashMap<String, Object>();
        details.put("user_id", userId);
        ((AbstractAuthenticationToken) token).setDetails(details);
        return token;
    }

    @PostMapping(path = "/logout", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Principal logout(Principal user, HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(
                AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY
        );
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);

        return user;
    }

    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    private User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping(value = "/addLoginAndPass", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean addUserAccess(@RequestBody UserAccessAndPass userAccessAndPass) {

        // Если пользователь с таким логином уже существует
        Optional<UserAccess> existingUser = userAccessRepository.findByLogin(userAccessAndPass.getUserAccess().getLogin());
        if (existingUser.isPresent()) {
            userRepository.deleteById(userAccessAndPass.getUserAccess().getUserId());
            return false;
        }

        userAccessRepository.save(new UserAccess(null, userAccessAndPass.getUserAccess().getLogin(), Role.USER,
                userAccessAndPass.getUserAccess().getUserId(), new Password(userAccessAndPass.getPassword()), true));
        return true;
    }
}
