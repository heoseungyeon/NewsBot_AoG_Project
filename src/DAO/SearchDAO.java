package DAO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class SearchDAO {

    @SuppressWarnings("unchecked")
    public JSONArray selectNews() throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONArray jsonArray = new JSONArray();
        try {
            //SQL Query
            String sql = "select * from article";


            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            System.out.println(rs);
            //각각 jsonObject에 넣고 jsonArray에 담음
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", rs.getString("title"));
                jsonObject.put("context", rs.getString("context"));
                jsonObject.put("url", rs.getString("url"));
                jsonObject.put("aid", rs.getString("aid"));
                jsonObject.put("word", rs.getString("word"));
                jsonObject.put("imgurl", rs.getString("imgurl"));

                jsonArray.add(jsonObject);

                jsonObject = null;
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

    }//selectNews()

    public JSONArray selectOneNews(String searchword) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONArray jsonArray = new JSONArray();
        try {
            //SQL Query
            String sql = "select * from article where word = ?";

            conn = DBConnection.getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, searchword);

            rs = pstmt.executeQuery();
            System.out.println(rs);
            //각각 jsonObject에 넣고 jsonArray에 담음
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", rs.getString("title"));
                jsonObject.put("context", rs.getString("context"));
                jsonObject.put("url", rs.getString("url"));
                jsonObject.put("aid", rs.getString("aid"));
                jsonObject.put("word", rs.getString("word"));
                jsonObject.put("imgurl", rs.getString("imgurl"));
                jsonArray.add(jsonObject);

                jsonObject = null;
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

    }//selectOneNews()

    public JSONObject selectDetailNews(String aid) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONObject jsonObject = new JSONObject();
        try {
            //SQL Query
            String sql = "select * from article where aid = ?";

            conn = DBConnection.getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, aid);

            rs = pstmt.executeQuery();
            System.out.println(rs);
            //각각 jsonObject에 넣고 jsonArray에 담음
            rs.next();
            jsonObject.put("title", rs.getString("title"));
            jsonObject.put("context", rs.getString("context"));
            jsonObject.put("url", rs.getString("url"));
            jsonObject.put("aid", rs.getString("aid"));
            jsonObject.put("word", rs.getString("word"));
            jsonObject.put("imgurl", rs.getString("imgurl"));


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
        return jsonObject;

    }//selectDetailNews()

    public String insertNews(JSONArray news) throws ClassNotFoundException, ParseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String rst = "success";
        try {
            conn = DBConnection.getConnection();
            // insert SQL Query


            int size = news.size();
            System.out.println("size: " + size);

            while (size != 0) {
                String sql = "insert IGNORE INTO article(title,context,url,aid,word,imgurl,fullcontext)"
                        + "values (?,?,?,?,?,?,?)";

                JSONObject jsonObject = (JSONObject) news.get(news.size() - size);
                System.out.println("하윙");
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, (jsonObject.get("title")).toString());
                pstmt.setString(2, (jsonObject.get("context")).toString());
                pstmt.setString(3, (jsonObject.get("url")).toString());
                pstmt.setString(4, (jsonObject.get("aid")).toString());
                pstmt.setString(5, (jsonObject.get("word")).toString());
                pstmt.setString(6, (jsonObject.get("imgurl")).toString());
                pstmt.setString(7, (jsonObject.get("fullcontext")).toString());

                pstmt.executeUpdate();
                size -= 1; //for loop
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
    }//insertNews

    public JSONObject updateNews(JSONObject news) throws ClassNotFoundException, ParseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONObject jsonObject = new JSONObject();
        String rst = "success";
        try {
            conn = DBConnection.getConnection();
            // insert SQL Query


            String sql = "update article set fullcontext=? where url=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, (news.get("fullcontext")).toString());
            pstmt.setString(2, (news.get("url")).toString());


            pstmt.executeUpdate();

            String sql2 = "select * from article where url =?";

            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, (news.get("url")).toString());


            rs = pstmt.executeQuery();
            rs.next();

            jsonObject.put("title", rs.getString("title"));
            jsonObject.put("fullcontext", rs.getString("fullcontext"));
            jsonObject.put("context", rs.getString("context"));
            jsonObject.put("url", rs.getString("url"));
            jsonObject.put("aid", rs.getString("aid"));
            jsonObject.put("word", rs.getString("word"));
            jsonObject.put("imgurl", rs.getString("imgurl"));

            System.out.println(rs);
            return jsonObject;

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
        return null;
    }//updateNews

    public JSONObject updateCategoryNews(JSONObject news) throws ClassNotFoundException, ParseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String rst = "success";
        JSONObject jsonObject = new JSONObject();

        try {
            conn = DBConnection.getConnection();
            // insert SQL Query


            String sql = "update categoryarticle set fullcontext=? where url=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, (news.get("fullcontext")).toString());
            pstmt.setString(2, (news.get("url")).toString());


            pstmt.executeUpdate();

            String sql2 = "select * from categoryarticle where url =?";

            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, (news.get("url")).toString());


            rs = pstmt.executeQuery();
            if (rs.next())
                jsonObject.put("title", rs.getString("title"));
            jsonObject.put("context", rs.getString("context"));
            jsonObject.put("fullcontext", rs.getString("fullcontext"));
            jsonObject.put("url", rs.getString("url"));
            jsonObject.put("aid", rs.getString("aid"));
            jsonObject.put("word", rs.getString("word"));

            System.out.println(rs);
            return jsonObject;


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
        return null;
    }//updateCategoryNews
}
