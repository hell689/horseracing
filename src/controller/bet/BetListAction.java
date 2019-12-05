package controller.bet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.Forward;
import domain.Bet;
import domain.User;
import service.BetService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class BetListAction extends Action {

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BetService betService = getServiceFactory().getBetService();
            User user = (User)req.getSession(false).getAttribute("currentUser");
            List<Bet> bets = null;
            switch(user.getRole()) {
            case CLIENT:
                bets = betService.findByUserId(user.getId());
                break;
            case BOOKMAKER:
            case ADMINISTRATOR:
                bets = betService.findAll();
            }   
            req.setAttribute("bets", bets);
            return null;
        } catch(FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }

}
