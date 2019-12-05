package dao.mysql;

import dao.BetDao;
import dao.DaoException;
import domain.Bet;
import domain.BetType;
import domain.Runner;
import domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BetDaoImpl extends BaseDaoImpl implements BetDao {
    
    @Override
    public List<Bet> readAll() throws DaoException {
        String sql = "SELECT `id`, `user_id`, `runner_id`, `bettype`, `bettime`, `finalrate`, `amount`, `win` FROM `bet`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().createStatement();
            resultSet = statement.executeQuery(sql);
            List<Bet> bets = new ArrayList<>();
            while(resultSet.next()) {
                Bet bet = new Bet();
                bet.setId(resultSet.getLong("id"));
                bet.setUser(new User());
                bet.getUser().setId(resultSet.getLong("user_id"));
                bet.setRunner(new Runner());
                bet.getRunner().setId(resultSet.getLong("runner_id"));
                bet.setBetType(BetType.values()[resultSet.getInt("bettype")]);
                bet.setBetTime(new java.util.Date(resultSet.getTimestamp("bettime").getTime()));
                bet.setFinalRate(resultSet.getBigDecimal("finalrate"));
                bet.setAmount(resultSet.getBigDecimal("amount"));
                bet.setWin(resultSet.getBoolean("win"));
                bets.add(bet);
            }
            return bets;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }
    
    @Override
    public List<Bet> readByUserId(Long userId) throws DaoException {
        String sql = "SELECT `id`, `user_id`, `runner_id`, `bettype`, `bettime`, `finalrate`, `amount`, `win` FROM `bet` WHERE `user_id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, userId);
            resultSet = statement.executeQuery();
            List<Bet> bets = new ArrayList<>();
            while(resultSet.next()) {
                Bet bet = new Bet();
                bet.setId(resultSet.getLong("id"));
                bet.setUser(new User());
                bet.getUser().setId(userId);
                bet.setRunner(new Runner());
                bet.getRunner().setId(resultSet.getLong("runner_id"));
                bet.setBetType(BetType.values()[resultSet.getInt("bettype")]);
                bet.setBetTime(new java.util.Date(resultSet.getTimestamp("bettime").getTime()));
                bet.setFinalRate(resultSet.getBigDecimal("finalrate"));
                bet.setAmount(resultSet.getBigDecimal("amount"));
                bet.setWin(resultSet.getBoolean("win"));
                bets.add(bet);
            }
            return bets;
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
            try{ resultSet.close(); } catch(Exception e) {}
        }
    }
    

    @Override
    public Bet read(Long id) throws DaoException {
        String sql = "SELECT  `user_id`, `runner_id`, `bettype`, `finalrate`, `bettime`, `amount`, `win` FROM `bet` WHERE  `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Bet bet = null;
            if (resultSet.next()){
                bet = new Bet();
                bet.setId(id);
                bet.setUser(new User());
                bet.getUser().setId(resultSet.getLong("user_id"));
                bet.setRunner(new Runner());
                bet.getRunner().setId(resultSet.getLong("runner_id"));
                bet.setBetType(BetType.values()[resultSet.getInt("bettype")]);
                bet.setBetTime(new java.util.Date(resultSet.getTimestamp("bettime").getTime()));
                bet.setFinalRate(resultSet.getBigDecimal("finalrate"));
                bet.setAmount(resultSet.getBigDecimal("amount"));
                bet.setWin(resultSet.getBoolean("win"));
            }
            return bet;
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
    public Long create(Bet bet) throws DaoException {
        String sql = "INSERT INTO `bet` (`user_id`, `runner_id`, `bettype`, "
                + "`bettime`, `finalrate`, `amount`, `win`) VALUES (?, ?, ?, ?, ?, ? ,?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, bet.getUser().getId());
            statement.setLong(2, bet.getRunner().getId());
            statement.setLong(3, bet.getBetType().ordinal());
            statement.setTimestamp(4, new Timestamp(bet.getBetTime().getTime()));
            statement.setBigDecimal(5, bet.getFinalRate());
            statement.setBigDecimal(6,bet.getAmount());
            statement.setBoolean(7, bet.isWin());
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
    public void update(Bet bet) throws DaoException {
        String sql = "UPDATE `bet` SET `user_id` = ?, `runner_id` = ?, `bettype` = ?, "
                + "`bettime` = ?, `finalrate` = ?, `amount` = ?, `win` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(sql);
            statement.setLong(1, bet.getUser().getId());
            statement.setLong(2, bet.getRunner().getId());
            statement.setLong(3, bet.getBetType().ordinal());
            statement.setTimestamp(4, new Timestamp(bet.getBetTime().getTime()));
            statement.setBigDecimal(5, bet.getFinalRate());
            statement.setBigDecimal(6, bet.getAmount());
            statement.setBoolean(7, bet.isWin());
            statement.setLong(8, bet.getId());
            statement.executeUpdate();
        } catch(SQLException e) {
            throw new DaoException(e);
        } finally {
            try{ statement.close(); } catch(Exception e) {}
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM `bet` WHERE `id` = ?";
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
