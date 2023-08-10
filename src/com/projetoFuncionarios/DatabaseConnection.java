import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection(String dbType, String url, String user, String password) throws SQLException {
        Connection connection = null;

        try {
            if (dbType.equalsIgnoreCase("sqlserver")) {
                String sqlServerUrl = "jdbc:sqlserver://" + url;
                connection = DriverManager.getConnection(sqlServerUrl, user, password);
            } else if (dbType.equalsIgnoreCase("oracle")) {
                String oracleUrl = "jdbc:oracle:thin:@" + url;
                connection = DriverManager.getConnection(oracleUrl, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
