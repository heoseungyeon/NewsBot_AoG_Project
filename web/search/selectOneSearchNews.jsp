<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="DAO.SearchDAO" %>

<%
    try{
        request.setCharacterEncoding("UTF-8");
        //파라 미터를 통해 데이터를 받음.
        String get_param = request.getParameter("searchword");
        System.out.println("get_param : " + get_param);
        //selectCategory()함수를 통해 category 테이블 전체 내용을 출력
        SearchDAO searchDAO = new SearchDAO();
        JSONArray list = searchDAO.selectOneNews(get_param);
        out.println(list.toString());
    } catch(Exception e) {
    }
%>
