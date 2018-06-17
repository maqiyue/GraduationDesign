//package db;
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///**
// * Created by maqiyue on 2018/5/19
// */
//public class JDBCTest {
//
//    DBConnection db = new DBConnection();
//
//    public static void main(String[] args){
//        JDBCTest jdbcTest = new JDBCTest();
//        //add(uname, uemail, upwd);
//        //update("��ʫʫ","lishishi@com","666");
//        //show();
//        jdbcTest.del("��С��");
//    }
//    //�������
//    public  int add(String uname,String uemail,String upwd) {
//        int i=0;
//        String sql="insert into employee (name,email,pwd) values (?,?,?)";
//
//        try {
//            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
//            preStmt.setString(1, uname);
//            preStmt.setString(2, uemail);
//            preStmt.setString(3, upwd);
//            preStmt.executeUpdate();
//            //Statement statement = (Statement) db.conn.createStatement();
//            //statement.executeUpdate(sql);
//
//            preStmt.close();
//            db.close();//�ر�����
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return i;//����Ӱ���������1Ϊִ�гɹ�
//    }
//    //���Ҳ���
//    public  void show(){
//        String sql ="select * from employee";
//
//        System.out.println("-----------------");
//        System.out.println("����" +"\t"+ "����" +"\t"+ "����");
//        System.out.println("-----------------");
//
//        try {
//            Statement stmt = (Statement) db.conn.createStatement();
//            ResultSet rs = (ResultSet) stmt.executeQuery(sql);
//            while(rs.next()){
//                String uname = rs.getString("name");
//                String uemail = rs.getString("email");
//                String uhiredate = rs.getString("hiredate");
//                //���Խ����ҵ���ֵд���࣬Ȼ�󷵻���Ӧ�Ķ���
//                //���� ��������Ķ˿���ʾһ��
//                System.out.println(uname +"\t"+ uemail +"\t"+ uhiredate);
//            }
//            rs.close();
//            db.close();//�ر�����
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //ɾ������
//    public int del(String uname) {
//        int i=0;
//        String sql="delete from employee where name=?";
//        try {
//            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
//            preStmt.setString(1, uname);
//            preStmt.executeUpdate();
//
//            preStmt.close();
//            db.close();//�ر�����
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//        return i;//����Ӱ���������1Ϊִ�гɹ�
//    }
//}