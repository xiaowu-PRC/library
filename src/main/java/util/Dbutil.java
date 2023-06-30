package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbutil {
    private final String dbUrl = "jdbc:mysql://192.168.110.253:3306/library";
    private final String dbUser = "root";
    private final String dbPass = "Lxcisasb1112138";

    public Connection getConnection() throws Exception {
        String jdbc = "com.mysql.cj.jdbc.Driver";
        Class.forName(jdbc);
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }

    public void close(Connection conn) throws Exception {
        if (conn != null) {
            conn.close();
        }
    }
}