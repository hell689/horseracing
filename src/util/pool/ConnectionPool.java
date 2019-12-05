package util.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class ConnectionPool {
    private static ConnectionPool instance = null;
    private Queue<Connection> freeConnections = new ConcurrentLinkedQueue<>();
    private Set<Connection> usedConnections = new ConcurrentSkipListSet<>();
    private int poolMaxSize;
    private int poolMinSize;
    private int validTimeout;
    private String url;
    private String user;
    private String pass;
    
    static {
        new DOMConfigurator().doConfigure("log4j.xml", LogManager.getLoggerRepository());
   }
   static Logger logger = Logger.getLogger(ConnectionPool.class);

    private ConnectionPool() throws SQLException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResourceBundle resource = ResourceBundle.getBundle("database");
        url = resource.getString("db.url")+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&allowPublicKeyRetrieval=true"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        user = resource.getString("db.user");
        pass = resource.getString("db.password");
        poolMaxSize = Integer.parseInt(resource.getString("poolMaxSize"));
        poolMinSize = Integer.parseInt(resource.getString("poolMinSize"));
        validTimeout = Integer.parseInt(resource.getString("validTimeout"));
        for (int i = 0; i < poolMinSize; i++){
            freeConnections.add(addConnection());
        }
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws PoolException {
        Connection connection = null;
        while(connection == null){
            connection = freeConnections.poll();
            try {
                if(connection != null) {                
                    if(!connection.isValid(validTimeout)){
                        close(connection);
                        connection = null;
                    } else if (usedConnections.size() < poolMaxSize){
                        connection = addConnection();
                    } else {
                        throw  new PoolException("connection limit reached");
                    }
                } else if (usedConnections.size() < poolMaxSize){
                    connection = addConnection();
                } else {
                    throw  new PoolException("connection limit reached");
                }
            } catch (SQLException e) {
                throw new PoolException(e);
            }
        }
        usedConnections.add(connection);
        logger.debug("Connection add. Used: " + usedConnections.size() + ";  Free: " + freeConnections.size());
        return connection;
    }

    void freeConnection (Connection connection) throws SQLException {
        try {
            usedConnections.remove(connection);
            connection.setAutoCommit(true);
            freeConnections.add(connection);
        } catch (SQLException e){
            close(connection);
            throw e;
        }
        logger.debug("Connection free. Used: " + usedConnections.size() + ";  Free: " + freeConnections.size());
    }

    private Connection addConnection() throws SQLException {
        return new WrapperConnector(DriverManager.getConnection(url, user, pass));
    }

    private void close(Connection connection) throws SQLException {
        synchronized (connection){
            connection.rollback();
            ((WrapperConnector)connection).getConnection().close();
            logger.debug("Connection closed. Used: " + usedConnections.size() + ";  Free: " + freeConnections.size());
        }
    }

    public void destroy() throws SQLException {
        synchronized (usedConnections){
            synchronized (freeConnections){
                usedConnections.addAll(freeConnections);
                freeConnections.clear();
                for (Connection connection : usedConnections){
                    close(connection);
                }
                usedConnections.clear();
            }
        }
    }
}
