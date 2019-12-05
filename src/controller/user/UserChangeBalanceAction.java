package controller.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import controller.Action;
import controller.Forward;
import domain.Race;
import domain.User;
import service.RaceService;
import service.UserService;
import service.exceptions.ServiceException;
import util.FactoryException;
import util.ServiceFactory;
import util.ServiceFactoryImpl;

public class UserChangeBalanceAction extends Action {
    
    static Logger logger = Logger.getLogger(UserChangeBalanceAction.class);
    
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User)req.getSession(false).getAttribute("currentUser");
        BigDecimal addBalance = null;
        try {
            addBalance = new BigDecimal(req.getParameter("addbalance"));
        } catch (NumberFormatException e) {}
        if (addBalance != null && addBalance.compareTo(BigDecimal.ZERO) > 0) {
            try (ServiceFactory service = new ServiceFactoryImpl();){
                UserService userService = service.getUserService();
                User user = userService.findById(currentUser.getId());
                user.setBalance(user.getBalance().add(addBalance));
                userService.save(user);
                logger.info("User " + user.getLogin() + " replenished balance on " + addBalance + ";");
                req.getSession().setAttribute("currentUser", user);
                req.setAttribute("user", user);
                return new Forward("/user/addbalance.html?message=" + URLEncoder.encode("Ваш баланс успешно пополнен", "UTF-8"));
            } catch(FactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (Throwable e) {
                throw new ServletException(e);
            } 
        } else {
            return new Forward("/user/addbalance.html?message=" + URLEncoder.encode("Неверно указана сумма пополнения", "UTF-8"));
        }
    }

}
