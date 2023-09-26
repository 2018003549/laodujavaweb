<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@page import="javaBean.Dept" %>
<%@page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>部门列表页面</title>
</head>
<body>

<script type="text/javascript">
    function del(dno) {
        // 弹出确认框，用户点击确定，返回true，点击取消返回false
        var ok = window.confirm("亲，删了不可恢复哦！");
        if (ok) {
            document.location.href = "<%=request.getContextPath()%>/dept/delete?deptno=" + dno;
        }
    }
</script>
<h1><%="欢迎"+session.getAttribute("username")+"登录"%></h1>
<h1 align="center">部门列表</h1>
<hr>
<table border="1px" align="center" width="50%">
    <tr>
        <th>序号</th>
        <th>部门编号</th>
        <th>部门名称</th>
        <th>操作</th>
    </tr>
    <%--			获取数据--%>
    <%
        List<Dept> deptList = (List<Dept>) request.getAttribute("deptList");
        int i=1;
        for (Dept dept : deptList) {
    %>
    <tr>
        <td><%=i%></td>
        <td><%=dept.getDeptno()%></td>
        <td><%=dept.getDname()%></td>
        <td>
            <a href="javascrpit:void(0)" onclick="del(<%=dept.getDeptno()%>)">删除</a>
            <a href="<%=request.getContextPath()%>/dept/detail?path=edit&deptno=<%=dept.getDeptno()%>">修改</a>
            <a href="<%=request.getContextPath()%>/dept/detail?path=detail&deptno=<%=dept.getDeptno()%>">详情</a>
        </td>
    </tr>
    <%
            i++;
        }
    %>

</table>

<hr>
<a href="<%=request.getContextPath()%>/add.jsp">新增部门</a>

</body>
</html>
