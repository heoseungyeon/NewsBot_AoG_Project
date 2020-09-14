package DAO;

import Crawler.Crawler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class RealtimeWordDAO {

    @SuppressWarnings("unchecked")
    public JSONArray selectRealtimeWord() throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JSONArray jsonArray = new JSONArray();
        try {
            //SQL Query
            String sql = "select * from realtimesearchword";


            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            System.out.println(rs);
            //각각 jsonObject에 넣고 jsonArray에 담음
            while (rs.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("idx",rs.getString("idx"));
                jsonObject.put("word",rs.getString("word"));
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

    }//selectRealtimeWord()

    public String insertRealtimeWord(JSONArray realtimeRank) throws ClassNotFoundException, ParseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String rst = "success";
        try {
            conn = DBConnection.getConnection();
            // insert SQL Query


            int size = realtimeRank.size();
            while(size!=0){
                String sql = "insert INTO realtimesearchword(idx,word)"
                        + "values (?,?)";

                JSONObject jsonObject = (JSONObject)realtimeRank.get(realtimeRank.size()-size);

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, (jsonObject.get("idx")).toString());
                pstmt.setString(2, (jsonObject.get("word")).toString());

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

    public String deleteRealtimeWord() throws ClassNotFoundException, ParseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String rst = "success";

        try {
            //SQL Query
            String sql = "delete from realtimesearchword";

            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.executeUpdate();

        } catch (SQLException sqle) {
            System.out.println("sql err2 : " + sqle.getMessage());
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
        System.out.println(rst);
        return rst;
    }//deleteRealtimeWord
}
