package controller.bet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import controller.Action;
import controller.Forward;
import domain.Bet;
import domain.BetType;
import domain.Runner;
import domain.User;
import service.BetService;
import service.RunnerService;
import service.UserService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class BetSaveAction extends Action {
    
    static Logger logger = Logger.getLogger(BetSaveAction.class);

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Bet bet = new Bet();
            Runner runner = new Runner();
            User user = (User)req.getSession(false).getAttribute("currentUser");
            try {
                    bet.setAmount(new BigDecimal(req.getParameter("amount")));
                    runner.setId(Long.parseLong(req.getParameter("runnerId")));
                    bet.setBetType(BetType.values()[Integer.parseInt(req.getParameter("betType"))]);
            } catch (NumberFormatException e) {}
            bet.setUser(user);
            if(bet.getUser().getBalance().compareTo(bet.getAmount()) < 0) {
                    return new Forward("/runner/list.html?raceId=" + req.getParameter("raceId") + "&message=" + URLEncoder.encode("Недостаточно денег на счету для этой ставки", "UTF-8"));
            } else {
                bet.setRunner(runner);
                bet.setBetTime(new Date());
                if(bet.getRunner().getId() != null && bet.getUser().getId() != null && bet.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    try {
                        BetService betService = getServiceFactory().getBetService();
                        RunnerService runnerService = getServiceFactory().getRunnerService();
                        bet.setRunner(runnerService.findById(bet.getRunner().getId()));
                        switch (bet.getBetType()) {
                        case WIN:
                            bet.setFinalRate(bet.getRunner().getRate());
                            break;
                        case NOT_WIN:
                            bet.setFinalRate(new BigDecimal(Math.sqrt(Math.sqrt(Math.sqrt(bet.getRunner().getRate().doubleValue())))).setScale(2, RoundingMode.HALF_UP));
                            break;
                        case WIN_OR_PRIZE:
                            bet.setFinalRate(new BigDecimal(Math.sqrt(bet.getRunner().getRate().doubleValue())).setScale(2, RoundingMode.HALF_UP));
                            break;
                        default:
                            bet.setFinalRate(bet.getRunner().getRate());
                        }
                        betService.save(bet);
                        UserService userService = getServiceFactory().getUserService();
                        user = userService.findById(bet.getUser().getId());
                        user.setBalance(user.getBalance().subtract(bet.getAmount()));
                        if(bet.getAmount().compareTo(new BigDecimal("50")) > 0) {
                            logger.info("Big bet User: " + bet.getUser().getLogin() + " Amount: " + bet.getAmount());
                        }
                        userService.save(user);
                        req.getSession().setAttribute("currentUser", user);
                    } catch (ServiceException | FactoryException e) {
                        throw new ServletException(e);
                    } catch (Throwable e) {
                        throw new ServletException(e);
                    }
                 }
                return new Forward("/bet/list.html?userId=" + user.getId());
            }
    }

}
