package controller.runner;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import controller.Action;
import controller.Forward;
import domain.Bet;
import domain.BetType;
import domain.Race;
import domain.User;
import service.BetService;
import service.RaceService;
import service.UserService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class RunnerFixedAction extends Action {
    
    static Logger logger = Logger.getLogger(RunnerFixedAction.class);

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long raceId = null;
        try {
            raceId = (Long.parseLong(req.getParameter("raceId")));
        } catch (NumberFormatException e) {}
        if(raceId != null) {
            try {
                BetService betService = getServiceFactory().getBetService();
                UserService userService = getServiceFactory().getUserService();
                RaceService raceService = getServiceFactory().getRaceService();
                List<Bet> bets = betService.findByRaceId(raceId);
                for(Bet bet : bets) {
                    User user = userService.findById(bet.getUser().getId());
                    if((bet.getBetType() == BetType.WIN && bet.getRunner().getPlace() == 1) || 
                            (bet.getBetType() == BetType.WIN_OR_PRIZE && (bet.getRunner().getPlace() == 1 || 
                            bet.getRunner().getPlace() == 2 || bet.getRunner().getPlace() == 3)) ||
                            bet.getBetType() == BetType.NOT_WIN && bet.getRunner().getPlace() != 1) {
                        user.setBalance(user.getBalance().add(bet.getFinalRate().multiply(bet.getAmount())));
                        bet.setWin(true);
                        userService.save(user);
                        logger.debug(bet.getUser().getLogin() + " win " + bet.getFinalRate().multiply(bet.getAmount()));
                    } else {
                        bet.setWin(false);
                        logger.debug(bet.getUser().getLogin() + " not win");
                    }
                    betService.save(bet);
                }
                Race race = raceService.findById(raceId);
                race.setFixResult(true);
                raceService.save(race);
            } catch (ServiceException | FactoryException e) {
                throw new ServletException(e);
            } catch (Throwable e) {
                throw new ServletException(e);
            }
        }
        return new Forward("/runner/list.html?raceId=" + req.getParameter("raceId"));
    }

}
