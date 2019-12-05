package controller.race;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.Forward;
import domain.Horse;
import domain.Race;
import service.HorseService;
import service.RaceService;
import service.exceptions.ServiceException;
import util.FactoryException;
import util.ServiceFactory;
import util.ServiceFactoryImpl;

public class RaceEditAction extends Action {

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch(NumberFormatException e) {}
        if(id != null) {
        try {
            RaceService raceService = getServiceFactory().getRaceService();
            Race race = raceService.findById(id);
            req.setAttribute("race", race);            
            SimpleDateFormat formatForTime = new SimpleDateFormat("HH:mm");
            String startTime = formatForTime.format(race.getDate());  
            SimpleDateFormat formatForDate = new SimpleDateFormat("yyyy-MM-dd");
            String startDate = formatForDate.format(race.getDate()); 
            req.setAttribute("startTime", startTime);
            req.setAttribute("startDate", startDate);
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (Throwable e) {
                throw new ServletException(e);
            }
        }
        return null;
    }

}
