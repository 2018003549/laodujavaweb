<%--
  Created by IntelliJ IDEA.
  User: LPW
  Date: 2023/1/27
  Time: 22:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/login" method="post">
    用户名:<input type="text" name="username">
    密码::<input type="password" name="password">
    <br>
    <input type="submit">
</form>
</body>
</html>
