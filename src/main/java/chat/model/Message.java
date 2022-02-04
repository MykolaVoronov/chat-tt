package chat.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private String text;
    private LocalDateTime sendTime;
    private User sender;

    public Message(String text, LocalDateTime sendTime, User sender) {
        this.text = text;
        this.sendTime = sendTime;
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    @Override
    public String toString() {
        return "Message{"
                + "id=" + id
                + ", text='" + text + '\''
                + '}';
    }
}
