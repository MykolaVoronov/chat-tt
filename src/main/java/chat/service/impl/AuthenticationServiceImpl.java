package chat.service.impl;

import chat.lib.Inject;
import chat.lib.Service;
import chat.model.User;
import chat.service.AuthenticationService;
import chat.service.UserService;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LogManager.getLogger(AuthenticationServiceImpl.class);
    @Inject
    private UserService userService;

    @Override
    public User login(String login) {
        logger.info("login method was called with params: login = {}", login);
        Optional<User> user = userService.findByLogin(login);
        return user.orElseGet(() -> userService.create(new User(login)));
    }
}
