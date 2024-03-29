package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import domain.User;
import service.UserService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class LoginAction extends Action {
    
    static Logger logger = Logger.getLogger(LoginAction.class);

	@Override
	public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	        String login = req.getParameter("login");
	        String password = req.getParameter("password");
	        if(login != null && password != null) {
	            try {
	                UserService service = getServiceFactory().getUserService();
	                User user = service.findByLoginAndPassword(login, password);
	                if(user != null) {
	                    HttpSession session = req.getSession();
	                    session.setAttribute("currentUser", user);
	                    logger.info("User: " + user.getLogin() + " logged in");
	                    return new Forward("/race/list.html");
	                } else {
	                    return new Forward("/login.html?message=" + URLEncoder.encode("Имя пользователя или пароль неопознанны", "UTF-8"));
	                }
	            } catch (FactoryException | ServiceException e) {
	                throw new ServletException(e);
	            } catch (Throwable e) {
	                throw new ServletException(e);
	            }
	        } else {
	            return null;
	        }
	}

}
