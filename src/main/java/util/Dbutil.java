package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Dbutil {
    private String dbUrl="jdbc:mysql://localhost:3306/library";
    private String dbUser="root";
    private String dbPass="Lxcisasb181107";
    private String jdbc="com.mysql.cj.jdbc.Driver";
    public Connection getConnection() throws Exception{
        Class.forName(jdbc);
        return DriverManager.getConnection(dbUrl,dbUser,dbPass);
    }
    public void close(Connection conn)throws Exception{
            if(conn!=null){
                conn.close();
            }
    }
}
