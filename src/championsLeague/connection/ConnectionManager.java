package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
  private static ConnectionManager instance;
  private Connection connection;

  private ConnectionManager() throws SQLException{
    connection = DriverManager.getConnection("jdbc:sqlite:champions.db");
  }

  public static ConnectionManager getInstance() throws SQLException{
    if ( instance == null ) {
      instance = new ConnectionManager();
    }
    return instance;
  }

  public Connection getConnection(){
    return this.connection;
  }
}
