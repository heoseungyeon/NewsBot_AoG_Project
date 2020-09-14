<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="DAO.CategoryDAO" %>
<%@ page import="org.json.simple.JSONArray" %>

<%
    try{
        //selectCategory()함수를 통해 category 테이블 전체 내용을 출력
        CategoryDAO categoryDAO = new CategoryDAO();
        JSONArray list = categoryDAO.selectCategory();
        out.println(list.toString());
    } catch(Exception e) {
    }
%>
