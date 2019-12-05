package service.logic;

import dao.BetDao;
import dao.DaoException;
import dao.HorseDao;
import dao.RaceDao;
import dao.RunnerDao;
import dao.UserDao;
import domain.Bet;
import service.BetService;
import service.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class BetServiceImpl extends BaseService implements BetService {
    private BetDao betDao;
    private UserDao userDao;
    private RunnerDao runnerDao;
    private HorseDao horseDao;
    private RaceDao raceDao;

    public void setBetDao(BetDao betDao) {
        this.betDao = betDao;
    }

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    public void setRunnerDao(RunnerDao runnerDao) {
        this.runnerDao = runnerDao;
    }
    
    public void setHorseDao(HorseDao horseDao) {
        this.horseDao = horseDao;
    }
    
    public void setRaceDao(RaceDao raceDao) {
        this.raceDao = raceDao;
    }

    @Override
    public Bet findById(Long id) throws ServiceException {
        try {
            Bet bet = betDao.read(id);
            bet.setUser(userDao.read(bet.getUser().getId()));
            bet.setRunner(runnerDao.read(bet.getRunner().getId()));
            return bet;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    
    @Override
    public List<Bet> findByUserId(Long userId) throws ServiceException {
            try {
        List<Bet> bets = betDao.readByUserId(userId);
        for(Bet bet : bets){
            bet.setUser(userDao.read(bet.getUser().getId()));
            bet.setRunner(runnerDao.read(bet.getRunner().getId()));
            bet.getRunner().setHorse(horseDao.read(bet.getRunner().getHorse().getId()));
            bet.getRunner().setRace(raceDao.read(bet.getRunner().getRace().getId()));
        }
        return bets;
        
    } catch (DaoException e) {
        throw new ServiceException(e);
    }
    }
    
    @Override
    public List<Bet> findByRaceId(Long raceId) throws ServiceException {
            List<Bet> raceBets = new ArrayList<>();
            List<Bet> allBets = findAll();
            for(Bet bet : allBets){
                if (bet.getRunner().getRace().getId() == raceId) {
                    raceBets.add(bet);
                }
            }
            return raceBets;
    }

    @Override
    public List<Bet> findAll() throws ServiceException {
        try {
            List<Bet> bets = betDao.readAll();
            for(Bet bet : bets){
                bet.setUser(userDao.read(bet.getUser().getId()));
                bet.setRunner(runnerDao.read(bet.getRunner().getId()));
                bet.getRunner().setHorse(horseDao.read(bet.getRunner().getHorse().getId()));
                bet.getRunner().setRace(raceDao.read(bet.getRunner().getRace().getId()));
            }
            return bets;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Bet bet) throws ServiceException {
        if(bet.getId() != null) {
            try {
                betDao.update(bet);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            try {
                betDao.create(bet);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            betDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
