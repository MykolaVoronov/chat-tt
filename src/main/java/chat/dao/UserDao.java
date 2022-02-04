package chat.dao;

import chat.model.User;
import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByLogin(String login);
}
