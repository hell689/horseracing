package controller.runner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.Forward;
import domain.Role;
import domain.Runner;
import domain.User;
import service.RunnerService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class RunnerEditAction extends Action {

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        
        BigDecimal rate = new BigDecimal("1.5");
        try {
            id = Long.parseLong(req.getParameter("id"));
            rate = new BigDecimal(req.getParameter("rate"));
        } catch(NumberFormatException e) {}
        if(id != null) {
        try {
            RunnerService runnerService = getServiceFactory().getRunnerService();
            
            Runner runner = runnerService.findById(id);
            User currentUser = (User)req.getSession(false).getAttribute("currentUser");
            if(currentUser.getRole() == Role.ADMINISTRATOR) {
                List<Runner> runners = runnerService.findByRaceId(runner.getRace().getId());
                List<Integer> places = new ArrayList<>();
                for (int i = 1; i <= runners.size(); i++) {
                    places.add(i);
                }
                for(Runner runn : runners) {
                    if(runn.getId() != runner.getId()) {
                        places.remove((Integer)runn.getPlace());
                    }
                }
                req.setAttribute("places", places);
            } else {
                runner.setRate(rate);
            }
            req.setAttribute("runner", runner);
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (Throwable e) {
                throw new ServletException(e);
            }
        }
        return null;
    }

}
