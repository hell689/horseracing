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

public class HorseEditAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(req.getParameter("id"));    
        } catch(NumberFormatException e) {}
        if(id != null) {
            try {
                HorseService service = getServiceFactory().getHorseService();
                Horse horse = service.findById(id);
                req.setAttribute("horse", horse);
            } catch (FactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        }        
        return null;
    }
}
