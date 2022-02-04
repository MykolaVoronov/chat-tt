package chat.service.impl;

import chat.lib.Injector;
import chat.model.User;
import chat.service.UserService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {
    private static Injector injector = Injector.getInstance("chat");
    private static UserService userService;
    private User petya;

    @BeforeAll
    static void beforeAll() {
        userService = (UserService) injector.getInstance(UserService.class);
    }

    @BeforeEach
    void setUp() {
        petya = userService.create(new User("petya"));
    }

    @AfterEach
    void tearDown() {
        List<User> users = userService.getAll();
        users.forEach(user -> userService.delete(user.getId()));
    }

    @Test
    void create_ok() {
        User user = new User("vasya");
        user = userService.create(user);
        boolean actual = user.getId() != null;
        Assertions.assertTrue(actual);
    }

    @Test
    void get_ok() {
       User userFromDb = userService.get(petya.getId());
       Assertions.assertEquals(petya, userFromDb);
    }

    @Test
    void getAll_ok() {
        List<User> expected = List.of(petya);
        List<User> actual = userService.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update_ok() {
        petya.setLogin("vasya");
        User userFromDb = userService.update(petya);
        Assertions.assertEquals(petya, userFromDb);
    }

    @Test
    void delete_ok() {
        boolean actual = userService.delete(petya.getId());
        Assertions.assertTrue(actual);
    }

    @Test
    void findByLogin_ok() {
        User userFromDb = userService.findByLogin(petya.getLogin()).get();
        Assertions.assertEquals(petya, userFromDb);
    }
}