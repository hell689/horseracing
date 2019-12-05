package dao;

import domain.Horse;

import java.util.List;

public interface HorseDao extends Dao<Horse> {
    List<Horse> readAll() throws DaoException;
}
