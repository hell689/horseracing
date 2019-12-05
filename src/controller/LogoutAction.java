package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import domain.User;

public class LogoutAction extends Action {
    
    static Logger logger = Logger.getLogger(LogoutAction.class);

	@Override
	public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	        HttpSession session = req.getSession(false);
	        if(session != null) {
	            User user = (User)req.getSession(false).getAttribute("currentUser");
	            logger.info("User: " + user.getLogin() + " logged out");
	            session.invalidate();
	        }
	        return new Forward("/login.html");
	}

}
