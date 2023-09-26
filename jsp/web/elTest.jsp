<%--
  Created by IntelliJ IDEA.
  User: LPW
  Date: 2023/1/30
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<% request.setAttribute("data","request");%>--%>
<% session.setAttribute("data","session");%>
${requestScope.data}
</body>
</html>
