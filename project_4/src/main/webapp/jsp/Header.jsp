<%@page import="in.co.rays.project_4.bean.RoleBean"%>
<%@page import="in.co.rays.project_4.controller.LoginCtl"%>
<%@page import="in.co.rays.project_4.controller.ORSView"%>
<%@page import="in.co.rays.project_4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Result System</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>


<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com//jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
.header {
	max-height: 130px;
	position: relative;
}

.front-control {
	margin-top: 7px;
}

.tbl {
	width: 100%;
	border-collapse: collapse;
}
</style>


<script>
	$(function() {
		$("#date").datepicker({

			changeMonth : true,
			changeYear : true,
			maxDate : 0,
			//minDate: 0+1,
			//beforeShowDay: noSunday,
			//beforeShowDay1: noSunday1,

			yearRange : "-67:",
		//defaultDate:"01/01/1999"
		});
	});
</script>



</head>
<body>
	<div class="header">
		<%
		UserBean userBean = (UserBean) session.getAttribute("user"); 

		boolean userLoggedIn = userBean != null;

		String welcomeMsg = "Hi, ";

		if (userLoggedIn) {
			String role = (String) session.getAttribute("role");
			welcomeMsg += userBean.getFirstName() + " (" + role + ")";
		} else {
			welcomeMsg += " Guest";
		}
		%>

		<table width="100%" border="0">

			<tr>
				<td width="90%"><a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a>
					| <%
				if (userLoggedIn) {
				%> <a
					href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>

					<%
					} else {
					%> <a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> <%
 }
 %></td>
				<td rowspan="2">
					<h1 align="Right">
						<img src="<%=ORSView.APP_CONTEXT%>/image/customLogo.png"
							width="180" height="50">
					</h1>
				</td>

			</tr>

			<tr>
				<td>
					<h3><%=welcomeMsg%></h3>
				</td>
			</tr>


			<%
			if (userLoggedIn) {
			%>

			<tr>
				<td colspan="2"><a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get
						Marksheet</b>
				</a> | <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet
						Merit List</b>
				</a> | <a href="<%=ORSView.MY_PROFILE_CTL%>">My Profile</b></a> | 
				<a
					href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> | <%
				if (userBean.getRoleId() == RoleBean.ADMIN) {
				%> 
				<a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> | 
				<a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
				<a href="<%=ORSView.USER_CTL%>">Add User</b></a> | 
				<a href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> | 
				<a href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> | 
			    <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | 
				<a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | 
				<a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | 
				<a href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> | 
				<a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> | 
				<a href="<%=ORSView.COURSE_CTL%>">Add Course </a> | <a
					href="<%=ORSView.COURSE_LIST_CTL%>">Course List</a> | <a
					href="<%=ORSView.SUBJECT_CTL%>">Add Subject</a> | <a
					href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</a> | <a
					href="<%=ORSView.FACULTY_CTL%>">Add Faculty</a> | <a
					href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</a> | <a
					href="<%=ORSView.TIME_TABLE_CTL%>">Add Time Table</a> | <a
					href="<%=ORSView.TIME_TABLE_LIST_CTL%>">TimeTable List</a> | <a
					href="<%=ORSView.JAVA_DOC_VIEW%>" target="_blank">Java Doc</b></a> <%
 }
 %></td>

			</tr>
			<%
			}
			%>
		</table>
		<hr>
	</div>
</body>
</html>