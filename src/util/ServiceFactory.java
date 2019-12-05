package util;

import dao.*;
import service.*;

import java.sql.Connection;

public interface ServiceFactory extends AutoCloseable {
    UserService getUserService() throws FactoryException;
    RunnerService getRunnerService() throws FactoryException;
    RaceService getRaceService() throws FactoryException;
    HorseService getHorseService() throws FactoryException;
    BetService getBetService() throws FactoryException;

    UserDao getUserDao() throws FactoryException;
    RunnerDao getRunnerDao() throws FactoryException;
    RaceDao getRaceDao() throws FactoryException;
    HorseDao getHorceDao() throws FactoryException;
    BetDao getBetDao() throws FactoryException;

    Connection getConnection() throws FactoryException;
    Transaction getTransaction() throws FactoryException;
}
