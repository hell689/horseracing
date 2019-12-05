package service.logic;

import dao.DaoException;
import dao.HorseDao;
import dao.RaceDao;
import dao.RunnerDao;
import domain.Runner;
import service.RunnerService;
import service.exceptions.ServiceException;

import java.util.List;

public class RunnerServiceImpl extends BaseService implements RunnerService {
    private RunnerDao runnerDao;
    private RaceDao raceDao;
    private HorseDao horseDao;

    public void setRunnerDao(RunnerDao runnerDao) {
        this.runnerDao = runnerDao;
    }

    public void setRaceDao(RaceDao raceDao) {
        this.raceDao = raceDao;
    }

    public void setHorseDao(HorseDao horseDao) {
        this.horseDao = horseDao;
    }


    @Override
    public Runner findById(Long id) throws ServiceException {
        try {
            Runner runner = runnerDao.read(id);
            runner.setRace(raceDao.read(runner.getRace().getId()));
            runner.setHorse(horseDao.read(runner.getHorse().getId()));
            return runner;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    
	@Override
	public List<Runner> findByRaceId(Long raceId) throws ServiceException {
		try {
            List<Runner> runners = runnerDao.readByRaceId(raceId);
            for(Runner runner : runners){
            	runner.setRace(raceDao.read(runner.getRace().getId()));
                runner.setHorse(horseDao.read(runner.getHorse().getId()));
            }
            return runners;
            
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
	}

    @Override
    public List<Runner> findAll() throws ServiceException {
        try {
            List<Runner> runners = runnerDao.readAll();
            for(Runner runner : runners){
                runner.setRace(raceDao.read(runner.getRace().getId()));
                runner.setHorse(horseDao.read(runner.getHorse().getId()));
            }
            return runners;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Runner runner) throws ServiceException {
        if(runner.getId() != null) {
            try {
                runnerDao.update(runner);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            try {
                runnerDao.create(runner);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            runnerDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setRate(Long id, float rate) throws ServiceException {
        try {
            runnerDao.updateRate(id, rate);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        
    }

}
