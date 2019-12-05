package controller.runner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import controller.Action;
import controller.Forward;
import domain.BetType;
import domain.Horse;
import domain.Runner;
import service.HorseService;
import service.RunnerService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class RunnerListAction extends Action {

    static Logger logger = Logger.getLogger(RunnerListAction.class);
    
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long raceId = null;
        try {
                raceId = Long.parseLong(req.getParameter("raceId"));
        } catch(NumberFormatException e) {}
        if(raceId != null) {
            try{
                RunnerService runnerService = getServiceFactory().getRunnerService();
                List<Runner> runners = runnerService.findByRaceId(raceId);
                req.setAttribute("runners", runners);
                String closed = "";
                Boolean fixed = null;
                if(runners.size() > 0 && new Date().after(runners.get(0).getRace().getDate())) {
                    closed = "disabled";
                    fixed = runners.get(0).getRace().isFixResult();
                }
                req.setAttribute("closed", closed);
                req.setAttribute("fixed", fixed);
                if (closed == "") {
                    HorseService horseService = getServiceFactory().getHorseService();
                    List<Horse> horses = horseService.findAll();
                    for(Runner runner : runners) {
                        horses.remove(runner.getHorse());
                    }
                    req.setAttribute("horses", horses);
                    req.setAttribute("betTypes", BetType.values());
                }
            } catch(FactoryException | ServiceException e) {
                 throw new ServletException(e);
            } catch (Throwable e) {
                throw new ServletException(e);
            }
        } else {
            try {
                RunnerService runnerService = getServiceFactory().getRunnerService();
                List<Runner> runners = runnerService.findAll();
                req.setAttribute("runners", runners);
                } catch(FactoryException | ServiceException e) {
                        throw new ServletException(e);
                        } catch (Throwable e) {
                                throw new ServletException(e);
                                }
        }
        return null;
    }

}
