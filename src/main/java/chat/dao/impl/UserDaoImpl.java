package chat.dao.impl;

import chat.dao.UserDao;
import chat.exception.DataProcessingException;
import chat.lib.Dao;
import chat.model.User;
import chat.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        String query = "INSERT INTO users (login, is_deleted) VALUES (?, FALSE);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement createStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, user.getLogin());
            createStatement.executeUpdate();
            ResultSet resultSet = createStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add user to db: " + user, e);
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM users WHERE id = ? AND is_deleted = FALSE;";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(query)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                user = parseUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get user from db by id: " + id, e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users WHERE is_deleted = FALSE;";
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                users.add(parseUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all messages", e);
        }
        return users;
    }

    @Override
    public User update(User user) {
        String query = "UPDATE users SET login = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(query)) {
            updateStatement.setString(1, user.getLogin());
            updateStatement.setLong(2, user.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update user: " + user, e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE users SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement deleteStatement = connection.prepareStatement(query)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete user by id: " + id, e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement findUserByLoginStatement = connection.prepareStatement(query)) {
            findUserByLoginStatement.setString(1, login);
            ResultSet resultSet = findUserByLoginStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = parseUserFromResultSet(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get user by login " + login, e);
        }
    }

    private User parseUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String login = resultSet.getString("login");
        User user = new User(login);
        user.setId(id);
        return user;
    }
}
