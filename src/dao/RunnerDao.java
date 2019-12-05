package dao;

import domain.Runner;

import java.util.List;

public interface RunnerDao extends Dao<Runner> {
    List<Runner> readAll() throws DaoException;
    List<Runner> readByRaceId(Long raceId) throws DaoException;
    void updateRate(Long id, float rate) throws DaoException;
}
