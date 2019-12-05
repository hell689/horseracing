package dao;

import domain.Bet;

import java.util.List;

public interface BetDao extends Dao<Bet> {
    List<Bet> readAll() throws DaoException;
    List<Bet> readByUserId(Long userId) throws DaoException;
}
