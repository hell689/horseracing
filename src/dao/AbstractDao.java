package dao;

import domain.Essence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDao <T extends Essence> {
    protected Connection connection;

    public AbstractDao(Connection connection){
        this.connection = connection;
    }

    public abstract List<T> findAll();

    public abstract T read(Long id) throws DaoException;

    public abstract Long create(T entity) throws DaoException;

    public abstract boolean update(T entity) throws DaoException;

    public abstract boolean delete(Long id) throws DaoException;

    public void close (Statement st){
        if (st != null){
            try {
                st.close();
            } catch (SQLException e) {
                System.err.println("Error Statement close: " + e.getMessage()) ;
                e.printStackTrace();
            }
        }
    }

}
