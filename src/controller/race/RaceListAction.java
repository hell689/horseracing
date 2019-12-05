package controller.race;

import java.io.IOException;
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

public class RaceListAction extends Action {

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RaceService raceService = getServiceFactory().getRaceService();
            List<Race> races = raceService.findAll();
            req.setAttribute("races", races);
            return null;
        } catch(FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }

}
