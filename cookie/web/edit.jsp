<%@ page import="javaBean.Dept" %>
<%@page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta charset="utf-8">
		<title>修改部门</title>
	</head>
	<body>
		<h1>修改部门</h1>
		<hr >
		<form action="<%=request.getContextPath()%>/dept/modify" method="post">
			部门编号<input type="text" name="deptno" value="${requestScope.dept.deptno}" readonly /><br>
			部门名称<input type="text" name="dname" value="${requestScope.dept.dname}"/><br>
			部门位置<input type="text" name="loc" value="${requestScope.dept.loc}"/><br>
			<input type="submit" value="修改"/><br>
		</form>
	</body>
</html>
