package dao;

import domain.Race;

import java.util.List;

public interface RaceDao extends Dao<Race> {
    List<Race> readAll() throws DaoException;
}
