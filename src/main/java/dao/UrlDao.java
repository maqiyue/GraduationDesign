package dao;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maqiyue on 2018/5/19
 */
public class UrlDao {
    DBConnection db = new DBConnection();

    public void add(List<String> list) {
        String sql = "insert into notice_url (n_url) values (?)";

        try {

            for (int j = 0; j < list.size(); j++) {
                PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
                preStmt.setString(1, list.get(j));
                preStmt.executeUpdate();
                preStmt.close();
            }
            db.close();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> selectAll(){
        List<String> list = new ArrayList<>();
        String sql ="select * from notice_url";
        try {
            Statement stmt = (Statement) db.conn.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            while(rs.next()){
                String url = rs.getString("n_url");
                list.add(url);
            }
            rs.close();
            db.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
