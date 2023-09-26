<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@page import="javaBean.Dept" %>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>部门列表页面</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
</head>
<body>

<script type="text/javascript">
    function del(dno) {
        // 弹出确认框，用户点击确定，返回true，点击取消返回false
        var ok = window.confirm("亲，删了不可恢复哦！");
        if (ok) {
            document.location = "<%=request.getContextPath()%>/dept/delete?deptno=" + dno;
        }
    }
</script>
欢迎${sessionScope.username}登录
当前用户数量：${applicationScope.count}
<a href="logout">退出</a>
<h1 align="center">部门列表</h1>
<hr>
<table border="1px" align="center" width="50%">
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>操作</th>
    </tr>

    <c:forEach items="${requestScope.deptList}" var="dept" varStatus="deptStatus">
        <tr>
            <td>${deptStatus.count}</td>
            <td>${dept.deptno}</td>
            <td>${dept.dname}</td>
            <td>
                <a href="javascript:void(0)" onclick="del(${dept.deptno})">删除</a>
                <a href="dept/detail?path=edit&deptno=${dept.deptno}">修改</a>
                <a href="dept/detail?path=detail&deptno=${dept.deptno}">详情</a>
            </td>
        </tr>
    </c:forEach>


</table>

<hr>
<a href="add.jsp">新增部门</a>

</body>
</html>
