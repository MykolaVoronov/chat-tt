package chat.service;

import chat.model.Message;
import java.util.List;

public interface MessageService extends GenericService<Message> {
    List<Message> getLastFiftyMessages();
}
