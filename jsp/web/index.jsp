<%@ page import="Bean.User" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
<% User user = new User("ada","sd",23);
  request.setAttribute("user",user);%>
  <%=request.getContextPath()%>
<br>
  <%=((HttpServletRequest)pageContext.getRequest()).getContextPath()%>
  ${initParam.money}<br>
  ${initParam.aaa}<br>
  ${empty paramValues.dadad ==null}
  </body>
</html>
