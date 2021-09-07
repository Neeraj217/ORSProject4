<%@page import="in.co.rays.project_4.model.RoleModel"%>
<%@page import="in.co.rays.project_4.util.HTMLUtility"%>
<%@page import="in.co.rays.project_4.bean.UserBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_4.controller.UserListCtl"%>
<%@page import="in.co.rays.project_4.util.ServletUtility"%>
<%@page import="in.co.rays.project_4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Online Result System</title>
<style type="text/css">
#content{
max-height:220px;

position: relative;
}
</style>
</head>
<div id="content">
<body>

	<%@ include file="Header.jsp"%>
	
	<center>
	<br>
		<h1>User List</h1>
		<jsp:useBean id="model" class="in.co.rays.project_4.model.RoleModel" scope ="request"></jsp:useBean>
		<jsp:useBean id="bean" class="in.co.rays.project_4.bean.UserBean"
			scope="request"></jsp:useBean>
		<%
			List l = (List) request.getAttribute("roleList");
		%>
		<form action="<%=ORSView.USER_LIST_CTL%>" method="post">
			<tr>
				<%
					

					List list = ServletUtility.getList(request);
					
					

				%>
				
				<td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
			</tr>
			<tr>
				<td colspan="8"><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></td>
			</tr>

			<table width="100%">
				<tr>
					<td align="center"><label>First Name :</label> <input
						type="text" name="firstName" placeholder="Enter First Name"
						value="<%=ServletUtility.getParameter("firstName", request)%>">
						&emsp; &emsp; <label>LoginId:</label> <input type="text"
						name="login" placeholder="Enter login here"
						value="<%=ServletUtility.getParameter("login", request)%>">

						&emsp; &emsp; <b>Role :</b> <%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), l)%>

						<input type="submit" name="operation"
						value="<%=UserListCtl.OP_SEARCH%>"> <input type="submit"
						name="operation" value="<%=UserListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			
<% if(list.size()!=0){%>
			<table border="2" width="100%">
				<tr>
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>
					<th>S.No</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>MobileNo</th>
					<th>DOB</th>
					<th>Role</th>
					<th>Edit</th>
				</tr>



				<%}%>
					<% int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;
					Iterator<UserBean> it = list.iterator();

					while (it.hasNext()) {
						 bean = it.next();
						 RoleBean rbean = model.findByPK(bean.getRoleId());
						rbean.getName(); 
				%>
				<tr>
					<td align="center"><input type="checkbox" class="checkbox"
						name="ids" value="<%=bean.getId()%>"
						<%if(bean.getRoleId()==RoleBean.ADMIN) {%> <%="disabled" %> <%} %>></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getFirstName()%></td>
					<td align="center"><%=bean.getLastName()%></td>
					<td align="center"><%=bean.getLogin()%></td>
					<td align="center"><%=bean.getGender()%></td>
					<td align="center"><%=bean.getMobileNo() %></td>
					<td align="center"><%=bean.getDob()%></td>
					<td align="center"><%=rbean.getId()%></td>
					<td align="center"><a href="UserCtl?id=<%=bean.getId()%>"
						<%if(bean.getRoleId()==RoleBean.ADMIN){ %> onclick="return false;"
						<%} %>>Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">
				<tr>

					<%if(pageNo==1){ %>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%} else{ %>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"></td>
					<%} %>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td>
					<%if(/* (model.nextPK()-1)==bean.getId() || */ list.size()<pageSize){ %>

					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=UserListCtl.OP_NEXT%>"></td>
					<%} else{ %>
					<td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"></td>
					<%} %>
				</tr>
			</table>
			<% if(list.size()==0) { %>
			<table>
				<tr>
					<td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
				</tr>
				<tr>
					<td colspan="8"><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></td>
				</tr>

				<tr>
					<td align="center"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_BACK %>"></td>
				</tr>
			</table>

			<%} %>
			</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body></div>
</html>