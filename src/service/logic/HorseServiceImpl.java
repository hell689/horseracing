package service.logic;

import dao.DaoException;
import dao.HorseDao;
import domain.Horse;
import service.HorseService;
import service.exceptions.ServiceException;

import java.util.List;

public class HorseServiceImpl extends BaseService implements HorseService {
    private HorseDao horseDao;

    public void setHorseDao(HorseDao horseDao) {
        this.horseDao = horseDao;
    }


    @Override
    public Horse findById(Long id) throws ServiceException {
        try {
            return horseDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Horse> findAll() throws ServiceException {
        try {
            return horseDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Horse horse) throws ServiceException {
        if(horse.getId() != null) {
            try {
                horseDao.update(horse);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            try {
                horseDao.create(horse);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            horseDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
