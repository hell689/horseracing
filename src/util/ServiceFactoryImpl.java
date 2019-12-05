package util;

import dao.*;
import dao.mysql.*;
import service.*;
import service.logic.*;
import util.pool.ConnectionPool;
import util.pool.PoolException;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ServiceFactoryImpl implements ServiceFactory {

    private Connection connection;
    static Logger logger = Logger.getLogger(ServiceFactoryImpl.class);

    @Override
    public UserService getUserService() throws FactoryException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setTransaction(getTransaction());
        userService.setUserDao(getUserDao());
        return userService;
    }

    @Override
    public RunnerService getRunnerService() throws FactoryException {
        RunnerServiceImpl runnerService = new RunnerServiceImpl();
        runnerService.setTransaction(getTransaction());
        runnerService.setHorseDao(getHorceDao());
        runnerService.setRaceDao(getRaceDao());
        runnerService.setRunnerDao(getRunnerDao());
        return runnerService;
    }

    @Override
    public RaceService getRaceService() throws FactoryException {
        RaceServiceImpl raceService = new RaceServiceImpl();
        raceService.setTransaction(getTransaction());
        raceService.setRaceDao(getRaceDao());
        return raceService;
    }

    @Override
    public HorseService getHorseService() throws FactoryException {
        HorseServiceImpl horseService = new HorseServiceImpl();
        horseService.setTransaction(getTransaction());
        horseService.setHorseDao(getHorceDao());
        return horseService;
    }

    @Override
    public BetService getBetService() throws FactoryException {
        BetServiceImpl betService = new BetServiceImpl();
        betService.setTransaction(getTransaction());
        betService.setBetDao(getBetDao());
        betService.setUserDao(getUserDao());
        betService.setRunnerDao(getRunnerDao());
        betService.setHorseDao(getHorceDao());
        betService.setRaceDao(getRaceDao());
        return betService;
    }

    @Override
    public UserDao getUserDao() throws FactoryException {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setConnection(getConnection());
        return userDao;
    }

    @Override
    public RunnerDao getRunnerDao() throws FactoryException {
        RunnerDaoImpl runnerDao = new RunnerDaoImpl();
        runnerDao.setConnection(getConnection());
        return runnerDao;
    }

    @Override
    public RaceDao getRaceDao() throws FactoryException {
        RaceDaoImpl raceDao = new RaceDaoImpl();
        raceDao.setConnection(getConnection());
        return raceDao;
    }

    @Override
    public HorseDao getHorceDao() throws FactoryException {
        HorseDaoImpl horseDao = new HorseDaoImpl();
        horseDao.setConnection(getConnection());
        return horseDao;
    }

    @Override
    public BetDao getBetDao() throws FactoryException {
        BetDaoImpl betDao = new BetDaoImpl();
        betDao.setConnection(getConnection());
        return betDao;
    }

    @Override
    public Connection getConnection() throws FactoryException {
        if(connection == null){
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (PoolException | SQLException e) {
                throw new FactoryException(e);
            }
        }
        return connection;
    }

    @Override
    public Transaction getTransaction() throws FactoryException {
        TransactionImpl transaction = new TransactionImpl();
        transaction.setConnection(getConnection());
        return transaction;
    }

    @Override
    public void close() {
        logger.debug("close connection " + connection);
        try {
            if(connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
