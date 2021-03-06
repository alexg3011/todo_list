package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.storage.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User dbUser = HbmStore.instOf().findUserByEmail(email);
        if (dbUser == null) {
            HbmStore.instOf().saveUser(User.of(name, email, password));
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Такой пользователь уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
