package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static final String URL = "jdbc:mysql://127.0.0.1/poetry";
    public static final String userName = "root";
    public static final String password = "";

    public static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(DBUtil.URL, DBUtil.userName, DBUtil.password);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
