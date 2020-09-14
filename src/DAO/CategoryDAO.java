package DAO;

import com.mysql.jdbc.log.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;


public class CategoryDAO { // Exercise 테이블에 접근하는 클래스

    @SuppressWarnings("unchecked")
    // Exercise테이블의 모든 내용 검색하여 JSONArray형태로 반환하는 Method
    public JSONArray selectCategory() throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONArray jsonArray = new JSONArray();
        try {
            //SQL Query
            String sql = "select * from category";


            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            System.out.println(rs);
            //각각 jsonObject에 넣고 jsonArray에 담음
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("idx",rs.getString("idx"));
                jsonObject.put("name",rs.getString("name"));
                jsonArray.add(jsonObject);

                jsonObject=null;
            }
        } catch (SQLException sqle) {
            System.out.println("sql err : " + sqle.getMessage());
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return jsonArray;

    }//selectCategory()

    public JSONArray selectCategoryNews() throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONArray jsonArray = new JSONArray();
        try {
            //SQL Query
            String sql = "select * from categoryarticle";


            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            System.out.println(rs);
            //각각 jsonObject에 넣고 jsonArray에 담음
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title",rs.getString("title"));
                jsonObject.put("context",rs.getString("context"));
                jsonObject.put("url",rs.getString("url"));
                jsonObject.put("category",rs.getString("category"));
                jsonObject.put("aid",rs.getString("aid"));

                jsonArray.add(jsonObject);

                jsonObject=null;
            }
        } catch (SQLException sqle) {
            System.out.println("sql err : " + sqle.getMessage());
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return jsonArray;

    }//selectCategory()

    public JSONArray selectOneCategoryNews(String category) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONArray jsonArray = new JSONArray();
        try {
            //SQL Query
            String sql = "select * from categoryarticle where category = (select idx from category where name = ?)";

            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,category);

            rs = pstmt.executeQuery();
            System.out.println(rs);
            //각각 jsonObject에 넣고 jsonArray에 담음
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title",rs.getString("title"));
                jsonObject.put("context",rs.getString("context"));
                jsonObject.put("url",rs.getString("url"));
                jsonObject.put("category",rs.getString("category"));
                jsonObject.put("aid",rs.getString("aid"));

                jsonArray.add(jsonObject);

                jsonObject=null;
            }
        } catch (SQLException sqle) {
            System.out.println("sql err : " + sqle.getMessage());
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return jsonArray;

    }//selectOneCategoryNews()

    public String insertCategoryNews(JSONArray categoryNews) throws ClassNotFoundException, ParseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String rst = "success";
        try {
            conn = DBConnection.getConnection();
            // insert SQL Query


            int size = categoryNews.size();
            while(size!=0){
                String sql = "insert IGNORE INTO categoryarticle(title,context,url,category,aid)"
                        + "values (?,?,?,?,?)";

                JSONObject jsonObject = (JSONObject)categoryNews.get(categoryNews.size()-size);

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, (jsonObject.get("title")).toString());
                pstmt.setString(2, (jsonObject.get("context")).toString());
                pstmt.setString(3, (jsonObject.get("url")).toString());
                pstmt.setString(4, (jsonObject.get("category")).toString());
                pstmt.setString(5, (jsonObject.get("aid")).toString());

                pstmt.executeUpdate();
                size -=1; //for loop
            }


        } catch (SQLException sqle) {
            System.out.println("sql err : " + sqle.getMessage());
            rst = sqle.getMessage();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                rst = "fail";
            }
        }
        return rst;
    }//insertRealtimeWord



}