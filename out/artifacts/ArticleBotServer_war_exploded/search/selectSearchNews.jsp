<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="DAO.SearchDAO" %>

<%
    try{
        //selectCategory()함수를 통해 category 테이블 전체 내용을 출력
        SearchDAO searchDAO = new SearchDAO();
            JSONArray list = searchDAO.selectNews();
        out.println(list.toString());
    } catch(Exception e) {
    }
%>
