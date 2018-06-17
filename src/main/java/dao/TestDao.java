package dao;

import com.google.gson.Gson;
import db.DBConnection;
import vo.NoticeArticle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by maqiyue on 2018/5/20
 */
public class TestDao {

    DBConnection db = new DBConnection();


    public String  selectUsr(String username,String password) {
        Gson gson = new Gson();
        String result = null;
        String sql = "select * from usr where username = '"+username+"' and username = '"+password+"'" ;
        try {
            Statement stmt = (Statement) db.conn.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String url = rs.getString("n_url");
                String title = rs.getString("n_title");
                String article = rs.getString("n_article");
                Usr usr = new Usr();
                usr.setName("111");
                result  = gson.toJson(url);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
