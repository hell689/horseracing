package service.logic;

import dao.DaoException;
import dao.RaceDao;
import domain.Race;
import service.RaceService;
import service.exceptions.ServiceException;

import java.util.List;

public class RaceServiceImpl extends BaseService implements RaceService {
    private RaceDao raceDao;

    public void setRaceDao(RaceDao raceDao) {
        this.raceDao = raceDao;
    }


    @Override
    public Race findById(Long id) throws ServiceException {
        try {
            return raceDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Race> findAll() throws ServiceException {
        try {
            return raceDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Race race) throws ServiceException {
        if(race.getId() != null) {
            try {
                raceDao.update(race);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            try {
                raceDao.create(race);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            raceDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
