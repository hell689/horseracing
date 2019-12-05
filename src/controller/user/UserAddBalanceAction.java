package controller.user;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.Forward;
import domain.Race;
import domain.User;
import service.RaceService;
import service.UserService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class UserAddBalanceAction extends Action {

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserService userService = getServiceFactory().getUserService();
            User currentUser = (User)req.getSession(false).getAttribute("currentUser");
            User user = userService.findById(currentUser.getId());
            req.setAttribute("user", user);
        } catch(FactoryException | ServiceException e) {
            throw new ServletException(e);
        } catch (Throwable e) {
            throw new ServletException(e);
        }
        return null;
    }

}
