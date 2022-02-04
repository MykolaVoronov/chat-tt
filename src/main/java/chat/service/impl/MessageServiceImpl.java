package chat.service.impl;

import chat.dao.MessageDao;
import chat.lib.Inject;
import chat.lib.Service;
import chat.model.Message;
import chat.service.MessageService;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Inject
    private MessageDao messageDao;

    @Override
    public Message create(Message message) {
        return messageDao.create(message);
    }

    @Override
    public Message get(Long id) {
        return messageDao.get(id).get();
    }

    @Override
    public List<Message> getAll() {
        return messageDao.getAll();
    }

    @Override
    public Message update(Message message) {
        return messageDao.update(message);
    }

    @Override
    public boolean delete(Long id) {
        return messageDao.delete(id);
    }

    @Override
    public List<Message> getLastFiftyMessages() {
        return messageDao.getLastFiftyMessages();
    }
}
