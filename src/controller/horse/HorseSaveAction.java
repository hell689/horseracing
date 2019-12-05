package controller.horse;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.Forward;
import domain.Horse;
import service.HorseService;
import service.exceptions.ServiceException;
import util.FactoryException;

public class HorseSaveAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Horse horse = new Horse();
        try {
            horse.setId(Long.parseLong(req.getParameter("id")));
        } catch (NumberFormatException e) {}
        horse.setName(req.getParameter("name"));
        if(horse.getName() != null) {
            try {
                HorseService service = getServiceFactory().getHorseService();
                service.save(horse);
            } catch (ServiceException | FactoryException e) {
                // TODO Auto-generated catch block
                throw new ServletException(e);
            }
        }
        return new Forward("/horse/list.html");
    }
}
