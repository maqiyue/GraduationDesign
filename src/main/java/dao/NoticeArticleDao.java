package dao;

import db.DBConnection;
import vo.NoticeArticle;
import vo.NoticeHtml;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maqiyue on 2018/5/20
 */
public class NoticeArticleDao {

    DBConnection db = new DBConnection();

//    public void add(List<NoticeArticle> list) {
//        String sql = "insert into notice_article (n_url,n_title,n_article) values (?,?,?)";
//        try {
//
//            for (NoticeArticle article : list) {
//                PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
//                preStmt.setString(1, article.getUrl());
//                preStmt.setString(2, article.getTitle());
//                preStmt.setString(3, article.getArticle());
//                preStmt.executeUpdate();
//                preStmt.close();
//            }
//            db.close();//�ر�����
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void add1(NoticeArticle article) {
        String sql = "insert into notice_article (n_url,n_title,n_article,n_time) values (?,?,?,?)";
        try {
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
            preStmt.setString(1, article.getUrl());
            preStmt.setString(2, article.getTitle());
            preStmt.setString(3, article.getArticle());
            preStmt.setString(4, article.getTime());
            preStmt.executeUpdate();
            preStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<NoticeArticle> selectAll() {
        List<NoticeArticle> list = new ArrayList<>();
        String sql = "select * from notice_article";
        try {
            Statement stmt = (Statement) db.conn.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String url = rs.getString("n_url");
                String title = rs.getString("n_title");
                String article = rs.getString("n_article");
                String time = rs.getString("n_time");
                list.add(new NoticeArticle(id,url,title,article,time));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public NoticeArticle selectById(int id) {
        NoticeArticle noticeArticle = null;
        String sql = "select * from notice_participle where id = '"+id+"'";
        try {
            Statement stmt = (Statement) db.conn.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            rs.next();
            String url = rs.getString("n_url");
            String title = rs.getString("n_title");
            String article = rs.getString("n_article");
            String time = rs.getString("n_time");
            noticeArticle = new NoticeArticle(url,title,article,time);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noticeArticle;
    }

    public String selectUrlById(int id) {
        String url  = null;
        String sql = "select * from notice_article where id = '"+id+"'";
        try {
            Statement stmt = (Statement) db.conn.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            rs.next();
            url = rs.getString("n_url");

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
