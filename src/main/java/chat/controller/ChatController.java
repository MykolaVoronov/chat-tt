package chat.controller;

import chat.lib.Injector;
import chat.model.Message;
import chat.service.MessageService;
import chat.service.UserService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChatController extends HttpServlet {
    private static final String SESSION_ATTRIBUTE_USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("chat");
    private final MessageService messageService
            = (MessageService) injector.getInstance(MessageService.class);
    private final UserService userService
            = (UserService) injector.getInstance(UserService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Message> messageList = messageService.getLastFiftyMessages();
        req.setAttribute("messages", messageList);
        req.getRequestDispatcher("/WEB-INF/views/chat.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String text = req.getParameter("text");
        HttpSession session = req.getSession();
        Long id = (Long) session.getAttribute(SESSION_ATTRIBUTE_USER_ID);
        Message message = new Message(text, LocalDateTime.now(), userService.get(id));
        messageService.create(message);
        resp.sendRedirect("/chat");
    }
}
