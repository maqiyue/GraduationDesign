package dao;

import db.DBConnection;
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
public class NoticeHtmlDao {

    DBConnection db = new DBConnection();

    public void add(List<NoticeHtml> list) {
        String sql = "insert into notice_html (n_url,n_html) values (?,?)";
        try {

            for (NoticeHtml html : list) {
                PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
                preStmt.setString(1, html.getUrl());
                preStmt.setString(2, html.getHtml());
                preStmt.executeUpdate();
                preStmt.close();
            }
            db.close();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add1(String s1, String s2) {
        String sql = "insert into notice_html (n_url,n_html) values (?,?)";
        try {
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
            preStmt.setString(1, s1);
            preStmt.setString(2, s2);
            preStmt.executeUpdate();
            preStmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<NoticeHtml> selectAll() {
        List<NoticeHtml> list = new ArrayList<>();
        String sql = "select * from notice_html";
        try {
            Statement stmt = (Statement) db.conn.createStatement();
            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
            while (rs.next()) {
                String url = rs.getString("n_url");
                String html = rs.getString("n_html");
                list.add(new NoticeHtml(url,html));
            }
            rs.close();
            db.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
