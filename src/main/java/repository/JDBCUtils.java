package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private  Connection instance=null;
    private Properties jdbcProperties;

    public JDBCUtils(Properties properties){
        this.jdbcProperties = properties;
    }

    /**
     * Returns a new connection to the database
     * @return connection
     */
    private Connection getNewConnection(){
        Connection con = null;

        String driver = jdbcProperties.getProperty("database.jdbc.driver");
        String url =jdbcProperties.getProperty("database.jdbc.url");

        try{
            con = DriverManager.getConnection(url);
            System.out.println("Connected");
        }catch(SQLException e){
            System.out.println("Error getting connection " + e);
        }

        return con;
    }

    /**
     * Method that returns a connection to the database
     * @return connection
     */
    public Connection getConnection()
    {
        try{
            if(instance == null || instance.isClosed())
                instance = getNewConnection();

        }catch(SQLException e){
            System.out.println("Error database " + e);

        }

        return instance;
    }
}
