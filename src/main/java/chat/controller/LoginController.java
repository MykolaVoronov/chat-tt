package chat.controller;

import chat.lib.Injector;
import chat.model.User;
import chat.service.AuthenticationService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends HttpServlet {
    private static final String SESSION_ATTRIBUTE_USER_ID = "user_id";
    private static final Injector injector = Injector.getInstance("chat");
    private final AuthenticationService authService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String login = req.getParameter("login");
        User user = authService.login(login);
        HttpSession session = req.getSession();
        session.setAttribute(SESSION_ATTRIBUTE_USER_ID, user.getId());
        resp.sendRedirect("/chat");
    }
}
