package dao.mysql;

import dao.DaoException;
import dao.HorseDao;
import domain.Horse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HorseDaoImpl extends BaseDaoImpl implements HorseDao {
    @Override
    public List<Horse> readAll() throws DaoException {
        String sql = "SELECT `id`, `name` FROM `horse`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            List<Horse> horses = new ArrayList<>();
            while(resultSet.next()) {
                Horse horse = new Horse();
                horse.setId(resultSet.getLong("id"));
                horse.setName(resultSet.getString("name"));
                horses.add(horse);
            }
            return horses;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public Horse read(Long id) throws DaoException {
        String sql = "SELECT  `name` FROM  `horse` WHERE  `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Horse horse = null;
            if (resultSet.next()){
                horse = new Horse();
                horse.setId(id);
                horse.setName(resultSet.getString("name"));
            }
            return horse;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Long create(Horse horse) throws DaoException {
        String sql = "INSERT INTO `horse` (`name`) VALUES (?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, horse.getName());
            statement.executeUpdate();
            Long id = null;
            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()) {
                id = resultSet.getLong(1);
            }
            return id;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{
                if (statement != null) {
                    statement.close();
                }
            } catch(Exception e) {e.printStackTrace();}
            try{
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch(Exception e) {e.printStackTrace();}
        }
    }

    @Override
    public void update(Horse horse) throws DaoException {
        String sql = "UPDATE `horse` SET `name` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, horse.getName());
            statement.setLong(2, horse.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM `horse` WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{
                if (statement != null) {
                    statement.close();
                }
            } catch(Exception e) {e.printStackTrace();}
        }
    }
}
