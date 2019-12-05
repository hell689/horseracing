package controller.horse;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.Forward;
import domain.Horse;
import service.HorseService;
import service.exceptions.ServiceException;
import util.FactoryException;
import util.ServiceFactory;
import util.ServiceFactoryImpl;

public class HorseListAction extends Action {

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HorseService horseService = getServiceFactory().getHorseService();
            List<Horse> horses = horseService.findAll();
            req.setAttribute("horses", horses);
            return null;
        } catch(FactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }

}
