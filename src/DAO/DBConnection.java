package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {	// DB 연결 클래스
    public static Connection getConnection() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = null;
        try {
            //자신의 DB 정보에 맞는 user와 pw 설정
            String user = "heo";
            String pw = "73007205";
            String url = "jdbc:mysql://localhost:3306/articlebot?serverTimezone=Asia/Seoul&characterEncoding=utf8";

            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("됐다");

        } catch (SQLException sqle) {
            System.out.println("DB error : " + sqle.toString());
        } catch (Exception e) {
            System.out.println("Unkonwn error");
        }
        return conn;
    }

}