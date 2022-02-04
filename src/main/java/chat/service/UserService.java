package chat.service;

import chat.model.User;
import java.util.Optional;

public interface UserService extends GenericService<User> {
    Optional<User> findByLogin(String login);
}
