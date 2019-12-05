package controller.runner;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Action;
import controller.Forward;
import domain.Runner;
import service.RunnerService;
import service.exceptions.ServiceException;
import util.FactoryException;
import util.ServiceFactory;
import util.ServiceFactoryImpl;

public class RunnerDeleteAction extends Action {

    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = null;
        try {
            id = Long.parseLong(req.getParameter("deleteId"));
        } catch(NumberFormatException e) {}
        if(id != null) {
            try {
                RunnerService runnerService = getServiceFactory().getRunnerService();
                runnerService.delete(id);
            } catch(FactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (Throwable e) {
                throw new ServletException(e);
            }
        }
        return new Forward("/runner/list.html?raceId=" + req.getParameter("raceId"));
    }

}
