<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="in.co.rays.project_4.bean.UserBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="bean" class =in.co.rays.project_4.bean.UserBean" scope="request"></jsp:useBean>

<h1><%=bean.getId() %></h1>
<h1><%=bean.getFirstName() %></h1>

</body>
</html>