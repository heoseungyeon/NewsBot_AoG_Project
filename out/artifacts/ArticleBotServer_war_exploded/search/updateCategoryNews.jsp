<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="DAO.SearchDAO" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="Crawler.Crawler" %>

<%
    try{
        Crawler crawler = new Crawler();

        //파라 미터를 통해 데이터를 받음.
        String get_param = request.getParameter("url");
        String url = get_param.replaceAll("@","&");
        System.out.println("get_param : " + get_param);
        System.out.println("url : " + url);
        //selectCategory()함수를 통해 category 테이블 전체 내용을 출력
        SearchDAO searchDAO = new SearchDAO();
        JSONObject list = searchDAO.updateCategoryNews(crawler.searchCategoryArticleContext(url));
        out.println(list.toString());
    } catch(Exception e) {
    }
%>
