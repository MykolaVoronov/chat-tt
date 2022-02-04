package chat.service.impl;

import chat.lib.Inject;
import chat.lib.Service;
import chat.model.User;
import chat.service.AuthenticationService;
import chat.service.UserService;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login) {
        Optional<User> user = userService.findByLogin(login);
        return user.orElseGet(() -> userService.create(new User(login)));
    }
}
