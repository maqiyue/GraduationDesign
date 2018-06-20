package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by maqiyue on 2018/5/19
 */
public class DBConnection {

//    String driver = "com.mysql.jdbc.Driver";
//    String url= "jdbc:mysql://localhost:3306/maqiyuegd";
//    String user = "root";
//    String password = "Yuege2018!";
String driver = "com.mysql.jdbc.Driver";
    String url= "jdbc:mysql://localhost:3306/maqiyuegd";
    String user = "root";
    String password = "rootg0822";

    public Connection conn;

    public DBConnection() {

        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}