package service;

import domain.Runner;
import service.exceptions.ServiceException;

import java.util.List;

public interface RunnerService {
    Runner findById(Long id) throws ServiceException;
    
    List<Runner> findByRaceId(Long raceId) throws ServiceException;

    List<Runner> findAll() throws ServiceException;

    void save(Runner runner) throws ServiceException;
    
    void setRate(Long id, float rate) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
