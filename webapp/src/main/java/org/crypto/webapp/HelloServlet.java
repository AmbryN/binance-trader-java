package org.crypto.webapp;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.crypto.bot.Trader;
import org.crypto.webapp.beans.Utilisateur;
import org.crypto.webapp.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    @Inject
    UserRepository userRepository;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Utilisateur> users = userRepository.findAll();
        request.setAttribute("user", users.get(0).getEmail());
        request.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    public void destroy() {
    }
}