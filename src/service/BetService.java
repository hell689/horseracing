package service;

import domain.Bet;
import service.exceptions.ServiceException;
import java.util.List;

public interface BetService {
    Bet findById(Long id) throws ServiceException;

    List<Bet> findAll() throws ServiceException;

    void save(Bet bet) throws ServiceException;

    void delete(Long id) throws ServiceException;

    List<Bet> findByUserId(Long userId) throws ServiceException;

    List<Bet> findByRaceId(Long raceId) throws ServiceException;
}
