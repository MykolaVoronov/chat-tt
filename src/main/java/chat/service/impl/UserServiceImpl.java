package chat.service.impl;

import chat.dao.UserDao;
import chat.lib.Inject;
import chat.lib.Service;
import chat.model.User;
import chat.service.UserService;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        logger.info("create method was called with params: user = {}", user);
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        logger.info("get method was called with params: id = {}", id);
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        logger.info("getAll method was called");
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        logger.info("update method was called with params: user = {}", user);
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("delete method was called with params: id = {}", id);
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        logger.info("findByLogin method was called with params: login = {}", login);
        return userDao.findByLogin(login);
    }
}
