package chat.service.impl;

import chat.dao.MessageDao;
import chat.lib.Inject;
import chat.lib.Service;
import chat.model.Message;
import chat.service.MessageService;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LogManager.getLogger(MessageServiceImpl.class);
    @Inject
    private MessageDao messageDao;

    @Override
    public Message create(Message message) {
        logger.info("create method was called with params: message = {}", message);
        return messageDao.create(message);
    }

    @Override
    public Message get(Long id) {
        logger.info("get method was called with params: id = {}", id);
        return messageDao.get(id).get();
    }

    @Override
    public List<Message> getAll() {
        logger.info("getAll method was called");
        return messageDao.getAll();
    }

    @Override
    public Message update(Message message) {
        logger.info("update method was called with params: message = {}", message);
        return messageDao.update(message);
    }

    @Override
    public boolean delete(Long id) {
        logger.info("delete method was called with params: id = {}", id);
        return messageDao.delete(id);
    }

    @Override
    public List<Message> getLastFiftyMessages() {
        logger.info("getLastFiftyMessages method was called");
        return messageDao.getLastFiftyMessages();
    }
}
