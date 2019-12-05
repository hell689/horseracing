package service;

import domain.Horse;
import service.exceptions.ServiceException;

import java.util.List;

public interface HorseService {
    Horse findById(Long id) throws ServiceException;

    List<Horse> findAll() throws ServiceException;

    void save(Horse horse) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
