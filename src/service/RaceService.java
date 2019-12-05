package service;

import domain.Race;
import service.exceptions.ServiceException;

import java.util.List;

public interface RaceService {
    Race findById(Long id) throws ServiceException;

    List<Race> findAll() throws ServiceException;

    void save(Race race) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
