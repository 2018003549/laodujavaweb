<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>hello</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>
当前用户数量：${applicationScope.count}
<form action="login" method="post">
    用户名:<input type="text" name="username">
    密码::<input type="password" name="password">
    <br>
    <input type="checkbox" name="Logofree" value="1">十天免登录
    <input type="submit">
</form>
</body>
</html>
