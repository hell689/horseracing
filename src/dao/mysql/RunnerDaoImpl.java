package dao.mysql;

import dao.DaoException;
import dao.RunnerDao;
import domain.Horse;
import domain.Race;
import domain.Runner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RunnerDaoImpl extends BaseDaoImpl implements RunnerDao {
    @Override
    public List<Runner> readAll() throws DaoException {
        String sql = "SELECT `id`, `race_id`, `horse_id`, `rate`, `place` FROM `runner`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            List<Runner> runners = new ArrayList<>();
            while(resultSet.next()) {
                Runner runner = new Runner();
                runner.setId(resultSet.getLong("id"));
                runner.setRace(new Race());
                runner.getRace().setId(resultSet.getLong("race_id"));
                runner.setHorse(new Horse());
                runner.getHorse().setId(resultSet.getLong("horse_id"));
                runner.setRate(resultSet.getBigDecimal("rate"));
                runner.setPlace(resultSet.getInt("place"));
                runners.add(runner);
            }
            return runners;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }
    
    public List<Runner> readByRaceId(Long raceId) throws DaoException {
        String sql = "SELECT `id`, `horse_id`, `rate`, `place` FROM `runner` WHERE `race_id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, raceId);
            resultSet = statement.executeQuery();
            List<Runner> runners = new ArrayList<>();
            while(resultSet.next()) {
                Runner runner = new Runner();
                runner.setId(resultSet.getLong("id"));
                runner.setRace(new Race());
                runner.getRace().setId(raceId);
                runner.setHorse(new Horse());
                runner.getHorse().setId(resultSet.getLong("horse_id"));
                runner.setRate(resultSet.getBigDecimal("rate"));
                runner.setPlace(resultSet.getInt("place"));
                runners.add(runner);
            }
            return runners;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }

    @Override
    public Runner read(Long id) throws DaoException {
        String sql = "SELECT  `race_id`, `horse_id`, `rate`, `place` FROM `runner` WHERE  `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Runner runner = null;
            if (resultSet.next()){
                runner = new Runner();
                runner.setId(id);
                runner.setRace(new Race());
                runner.getRace().setId(resultSet.getLong("race_id"));
                runner.setHorse(new Horse());
                runner.getHorse().setId(resultSet.getLong("horse_id"));
                runner.setRate(resultSet.getBigDecimal("rate"));
                runner.setPlace(resultSet.getInt("place"));
            }
            return runner;
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
    public Long create(Runner runner) throws DaoException {
        String sql = "INSERT INTO `runner` (`race_id`, `horse_id`, `rate`, `place`) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, runner.getRace().getId());
            statement.setLong(2, runner.getHorse().getId());
            statement.setBigDecimal(3, runner.getRate());
            statement.setInt(4, runner.getPlace());
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
    public void update(Runner runner) throws DaoException {
        String sql = "UPDATE `runner` SET `race_id` = ?, `horse_id` = ?, `rate` = ?, `place` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, runner.getRace().getId());
            statement.setLong(2, runner.getHorse().getId());
            statement.setBigDecimal(3, runner.getRate());
            statement.setInt(4, runner.getPlace());
            statement.setLong(5, runner.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }
    
    @Override
    public void updateRate(Long id, float rate) throws DaoException {
        String sql = "UPDATE `runner` SET `rate` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setFloat(1, rate);
            statement.setLong(5, id);
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM `runner` WHERE `id` = ?";
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
