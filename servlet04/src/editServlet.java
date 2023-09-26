import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class editServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //获取部门编号
        String deptno = request.getParameter("deptno");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();
            String contextPath = request.getContextPath();
            //编写sql，获取预编译数据库操作对象
            String sql = "select deptno,dname,loc from dept where deptno='" + deptno + "'";
            ps = connection.prepareStatement(sql);
            //执行sql
            rs = ps.executeQuery();
            int i = 0;
            //处理结果集,如果查询成功就只有一条数据，用if就可以了
            if (rs.next()) {
                String name = rs.getString("dname");
                String loc = rs.getString("loc");
                out.print(deptno);
                out.print("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "\t<head>\n" +
                        "\t\t<meta charset=\"utf-8\">\n" +
                        "\t\t<title>修改部门</title>\n" +
                        "\t</head>\n" +
                        "\t<body>\n" +
                        "\t\t<h1>修改部门</h1>\n" +
                        "\t\t<hr >\n" +
                        "\t\t<form action="+contextPath+"/dept/modify method=\"post\">\n" +
                        "\t\t\t部门编号<input type=\"text\" name=\"deptno\" value=" + deptno +" readonly/><br>\n" +
                        "\t\t\t部门名称<input type=\"text\" name=\"dname\" value=\"" + name + "\"/><br>\n" +
                        "\t\t\t部门位置<input type=\"text\" name=\"loc\" value=\"" + loc + "\"/><br>\n" +
                        "\t\t\t<input type=\"submit\" value=\"修改\"/><br>\n" +
                        "\t\t</form>\n" +
                        "\t</body>\n" +
                        "</html>\n");
            }
            out.println("<input type=\"button\" value=\"后退\" onclick=\"window.history.back()\"/>");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}

