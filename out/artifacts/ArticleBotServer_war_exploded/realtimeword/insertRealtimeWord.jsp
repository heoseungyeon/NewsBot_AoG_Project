
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="DAO.RealtimeWordDAO"%>
<%@ page import="Crawler.Crawler" %>
<%
	request.setCharacterEncoding("UTF-8");
	//파라 미터를 통해 데이터를 받음.
//	String get_param = request.getParameter("realtimeword_insert");
//	System.out.println("get_param : " + get_param);
//	JSONParser parser = new JSONParser();
//	//파서를 통해 파싱 후 jsonObject에 담음
//	Object obj = parser.parse(get_param);
//	JSONObject jsonObj = (JSONObject) obj;
//	System.out.println("test1 : " + jsonObj);

	Crawler crawler = new Crawler();
	RealtimeWordDAO realtimeWordDAO = new RealtimeWordDAO();
	//전체 삭제 후
	realtimeWordDAO.deleteRealtimeWord();
	//전체 입력
	String rst = realtimeWordDAO.insertRealtimeWord(crawler.realtimeSearchWord());
	//더미 값 일단 출력
	out.println(rst);
%>