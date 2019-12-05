package controller.runner;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import controller.Action;
import controller.Forward;
import controller.user.UserChangeBalanceAction;
import domain.Horse;
import domain.Race;
import domain.Role;
import domain.Runner;
import domain.User;
import service.RunnerService;
import service.exceptions.ServiceException;
import util.FactoryException;
import util.ServiceFactory;
import util.ServiceFactoryImpl;

public class RunnerSaveAction extends Action {
    
    static Logger logger = Logger.getLogger(RunnerSaveAction.class);

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Runner runner = new Runner();
        Horse horse = new Horse();
        Race race = new Race();
        User currentUser = (User)req.getSession(false).getAttribute("currentUser");
        try {
            if (req.getParameter("id") != null) {
                runner.setId(Long.parseLong(req.getParameter("id")));
            }
            horse.setId(Long.parseLong(req.getParameter("horseId")));
            race.setId(Long.parseLong(req.getParameter("raceId")));
            runner.setRate(new BigDecimal(req.getParameter("rate")));
            if (currentUser.getRole() == Role.ADMINISTRATOR) {
                runner.setPlace(Integer.parseInt(req.getParameter("place")));
            }
        } catch (NumberFormatException e) {}
            runner.setHorse(horse);
            runner.setRace(race);
        if(runner.getHorse().getId() != null && runner.getRace().getId() != null) {
            try {
                RunnerService runnerService = getServiceFactory().getRunnerService();
                runnerService.save(runner);
            } catch (ServiceException | FactoryException e) {
                throw new ServletException(e);
            } catch (Throwable e) {
                throw new ServletException(e);
            }
        }
        return new Forward("/runner/list.html?raceId=" + req.getParameter("raceId"));
    }

}
