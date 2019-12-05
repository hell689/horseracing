package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;

public class StartPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    
    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("currentUser") != null) {
                User user = (User)session.getAttribute("currentUser");
                switch(user.getRole()) {
                        case ADMINISTRATOR:
                        case BOOKMAKER:
                        case CLIENT:
                                response.sendRedirect(request.getContextPath() + "/race/list.html");
                                break;
                }
        } else {
                response.sendRedirect(request.getContextPath() + "/login.html");
        }
}

}
