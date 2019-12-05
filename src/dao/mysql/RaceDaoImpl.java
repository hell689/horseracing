package dao.mysql;

import dao.DaoException;
import dao.RaceDao;
import domain.Race;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaceDaoImpl extends BaseDaoImpl implements RaceDao {
    @Override
    public List<Race> readAll() throws DaoException {
        String sql = "SELECT `id`, `name`, `date`, `fixresult` FROM `race`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            List<Race> races = new ArrayList<>();
            while(resultSet.next()) {
                Race race = new Race();
                race.setId(resultSet.getLong("id"));
                race.setName(resultSet.getString("name"));
                race.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
                race.setFixResult(resultSet.getBoolean("fixresult"));
                races.add(race);
            }
            return races;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public Race read(Long id) throws DaoException {
        String sql = "SELECT  `name`, `date`, `fixresult` FROM  `race` WHERE  `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Race race = null;
            if (resultSet.next()){
                race = new Race();
                race.setId(id);
                race.setName(resultSet.getString("name"));
                race.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
                race.setFixResult(resultSet.getBoolean("fixresult"));
            }
            return race;
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
    public Long create(Race race) throws DaoException {
        String sql = "INSERT INTO `race` (`name`, `date`, `fixresult`) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, race.getName());
            statement.setTimestamp(2, new Timestamp(race.getDate().getTime()));
            statement.setBoolean(3,race.isFixResult());
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
    public void update(Race race) throws DaoException {
        String sql = "UPDATE `race` SET `name` = ?, `date` = ?, `fixresult` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, race.getName());
            statement.setTimestamp(2, new Timestamp(race.getDate().getTime()));
            statement.setBoolean(3,race.isFixResult());
            statement.setLong(4, race.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM `race` WHERE `id` = ?";
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
