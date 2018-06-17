package dao;

import com.google.gson.Gson;
import db.DBConnection;
import vo.Participle;
import vo.RangeFrequency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by maqiyue on 2018/5/20
 */
public class ParticipleDao {

    DBConnection db = new DBConnection();



    public void addArticle(Map<String, RangeFrequency> map) {
        String sql = "insert into participle_article (p_key,p_range,p_frequency,p_result_count) values (?,?,?,?)";
        try {
            for(Map.Entry<String,RangeFrequency> entry:map.entrySet()){
                RangeFrequency rangeFrequency = entry.getValue();
                PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
                preStmt.setString(1, entry.getKey());
                preStmt.setString(2, new Gson().toJson(rangeFrequency.getRange()));
                preStmt.setInt(3,rangeFrequency.getFrequency());
                preStmt.setInt(4,rangeFrequency.getRange().size());
                preStmt.executeUpdate();
                preStmt.close();
            }
            db.close();//�ر�����
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTitle(Map<String, RangeFrequency> map) {
        String sql = "insert into participle_title (p_key,p_range,p_frequency,p_result_count) values (?,?,?,?)";
        try {
            for(Map.Entry<String,RangeFrequency> entry:map.entrySet()){
                RangeFrequency rangeFrequency = entry.getValue();
                PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
                preStmt.setString(1, entry.getKey());
                preStmt.setString(2, new Gson().toJson(rangeFrequency.getRange()));
                preStmt.setInt(3,rangeFrequency.getFrequency());
                preStmt.setInt(4,rangeFrequency.getRange().size());
                preStmt.executeUpdate();
                preStmt.close();
            }
            db.close();//�ر�����
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<Double> selectArticle(String key) {
        Set<Double> set = null;
        String range = null;
        String sql = "select * from participle_article where p_key = '"+key+"'";
        try {
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
            ResultSet rs = (ResultSet) preStmt.executeQuery(sql);
            if (rs.next()){
                range = rs.getString("p_range");
            }
                set = new Gson().fromJson(range,Set.class);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    public Set<Double> selectTitle(String key) {
        Set<Double> set = null;
        String sql = "select * from participle_title where p_key = '"+key+"'";
        try {
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);

            ResultSet rs = (ResultSet) preStmt.executeQuery(sql);
            rs.next();
            String range = rs.getString("p_range");
            set = new Gson().fromJson(range,Set.class);
            rs.close();
            db.close();//�ر�����
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }
}
