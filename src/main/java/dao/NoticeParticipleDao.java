package dao;

import db.DBConnection;
import vo.NoticeArticle;
import vo.Participle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maqiyue on 2018/5/20
 */
public class NoticeParticipleDao {

    DBConnection db = new DBConnection();



    public void add1(Participle participle) {
        String sql = "insert into notice_participle (id,n_title,n_article,n_url,n_time) values (?,?,?,?,?)";
        try {
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
            preStmt.setInt(1, participle.getId());
            preStmt.setString(2, participle.getTitle());
            preStmt.setString(3, participle.getArticle());
            preStmt.setString(4, participle.getUrl());
            preStmt.setString(5, participle.getTime());
            preStmt.executeUpdate();
            preStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Participle> selectAll() {
        List<Participle> list = new ArrayList<>();
        String sql = "select * from notice_participle";
        try {
            Statement stmt = (Statement) db.conn.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("n_title");
                String article = rs.getString("n_article");
                list.add(new Participle(id,title,article,null));
            }
            rs.close();
            db.close();//�ر�����
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
