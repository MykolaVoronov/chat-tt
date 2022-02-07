package chat.dao.impl;

import chat.dao.MessageDao;
import chat.exception.DataProcessingException;
import chat.lib.Dao;
import chat.model.Message;
import chat.model.User;
import chat.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class MessageDaoImpl implements MessageDao {
    private static final Long MESSAGES_COUNT = 50L;

    @Override
    public Message create(Message message) {
        String query = "INSERT INTO messages (text, send_time, user_id)"
                + " VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                   PreparedStatement createStatement = connection.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS)) {
            createStatement.setString(1, message.getText());
            createStatement.setTimestamp(2, Timestamp.valueOf(message.getSendTime()));
            createStatement.setLong(3, message.getSender().getId());
            createStatement.executeUpdate();
            ResultSet resultSet = createStatement.getGeneratedKeys();
            if (resultSet.next()) {
                message.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t add message to db: " + message, e);
        }
        return message;
    }

    @Override
    public Optional<Message> get(Long id) {
        String query = "SELECT m.id, m.text, m.send_time, u.id, u.login "
                + "FROM messages m "
                + "JOIN users u ON m.user_id = u.id "
                + "WHERE m.id = ? AND m.is_deleted = FALSE;";
        Message message = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getStatement = connection.prepareStatement(query)) {
            getStatement.setLong(1, id);
            ResultSet resultSet = getStatement.executeQuery();
            if (resultSet.next()) {
                message = parseMessageFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get message from db by id: " + id, e);
        }
        return Optional.ofNullable(message);
    }

    @Override
    public List<Message> getAll() {
        String query = "SELECT m.id, m.text, m.send_time, u.id, u.login "
                + "FROM messages m "
                + "JOIN users u ON m.user_id = u.id "
                + "WHERE m.is_deleted = FALSE;";
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                messages.add(parseMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get all messages", e);
        }
        return messages;
    }

    @Override
    public Message update(Message message) {
        String query = "UPDATE messages SET text = ? WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(query)) {
            updateStatement.setString(1, message.getText());
            updateStatement.setLong(2, message.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t update message: " + message, e);
        }
        return message;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE messages SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(query)) {
            deleteStatement.setLong(1, id);
            return deleteStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t delete message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getLastFiftyMessages() {
        String query = "SELECT m.id, m.text, m.send_time, u.id AS user_id, u.login "
                + "FROM messages m "
                + "JOIN users u ON m.user_id = u.id "
                + "WHERE m.is_deleted = FALSE "
                + "ORDER BY m.send_time "
                + "LIMIT ?;";
        List<Message> messages = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getLastFifty = connection.prepareStatement(query)) {
            getLastFifty.setLong(1, MESSAGES_COUNT);
            ResultSet resultSet = getLastFifty.executeQuery();
            while (resultSet.next()) {
                messages.add(parseMessageFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t get last 50 messages", e);
        }
        return messages;
    }

    private Message parseMessageFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getObject("user_id", Long.class);
        String login = resultSet.getString("login");
        User sender = new User(login);
        sender.setId(userId);
        Long id = resultSet.getObject("id", Long.class);
        String text = resultSet.getString("text");
        LocalDateTime sendTime = resultSet.getObject("send_time", LocalDateTime.class);
        Message message = new Message(text, sendTime, sender);
        message.setId(id);
        return message;
    }
}
