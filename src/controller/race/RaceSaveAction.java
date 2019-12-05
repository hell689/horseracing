package controller.race;

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
import service.RaceService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class RaceSaveAction extends Action {

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Race race = new Race();
        try {
            race.setId(Long.parseLong(req.getParameter("id")));
        } catch (NumberFormatException e) {}
        Date date = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
        try {
                String strDate = req.getParameter("date") + " " + req.getParameter("time") + ":00";
                System.out.println(strDate);
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServletException(e);}
        race.setName(req.getParameter("name"));
        race.setDate(date);
        System.out.println(date);
        if(race.getName() != null && race.getDate() != null) {
            try {
                RaceService raceService = getServiceFactory().getRaceService();
                raceService.save(race);
            } catch (ServiceException | FactoryException e) {
                throw new ServletException(e);
            } catch (Throwable e) {
                throw new ServletException(e);
            }
        }
        return new Forward("/race/list.html");
    }

}
