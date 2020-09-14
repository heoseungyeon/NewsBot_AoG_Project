<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="DAO.CategoryDAO" %>
<%@ page import="org.json.simple.JSONArray" %>

<%
    try{
        request.setCharacterEncoding("UTF-8");
        //파라 미터를 통해 데이터를 받음.
        String get_param = request.getParameter("category");
        System.out.println("get_param : " + get_param);
        //selectCategory()함수를 통해 category 테이블 전체 내용을 출력
        CategoryDAO categoryDAO = new CategoryDAO();
        JSONArray list = categoryDAO.selectOneCategoryNews(get_param);
        out.println(list.toString());
    } catch(Exception e) {
    }
%>
