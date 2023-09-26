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

public class ListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        //获取项目路径
        String contextPath = request.getContextPath();
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        out.print(      "                          <!DOCTYPE html>                                      "                 );
        out.print(      "                  <html>");
        out.print(      "                      <head>");
        out.print(      "                          <meta charset='utf-8'>");
        out.print(      "                          <title>部门列表页面</title>");
        out.print(      "                      </head>");
        out.print(      "                      <body>");
        out.print("<script type=\"text/javascript\">\n" +
                "\tfunction del(dno){\n" +
                "\t\tif(window.confirm(\"亲，删了不可恢复哦！\")){\n" +
                "\t\t\tdocument.location.href = \"/oa/dept/delete?deptno=\" + dno;\n" +
                "\t\t}\n" +
                "\t}\n" +
                "</script>");
        out.print(      "                          <h1 align='center'>部门列表</h1>");
        out.print(      "                          <hr >");
        out.print(      "                          <table border='1px' align='center' width='50%'>");
        out.print(      "                              <tr>");
        out.print(      "                                  <th>序号</th>");
        out.print(      "                                  <th>部门编号</th>");
        out.print(      "                                  <th>部门名称</th>");
        out.print(      "                                  <th>操作</th>");
        out.print(      "                              </tr>");
        try {
            connection = DBUtils.getConnection();
            //编写sql，获取预编译数据库操作对象
            String sql = "select deptno,dname,loc from dept";
            ps = connection.prepareStatement(sql);
            //执行sql
            rs = ps.executeQuery();
            int i=0;
            //处理结果集
            while(rs.next()){
                String deptno = rs.getString("deptno");//根据查询的字段名获取信息，如果起别名则改成别名
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                out.print(      "                              <tr>");
                out.print(      "  <td>" +(++i) + "</td>");
                out.print(      "                                  <td>"+deptno+"</td>");
                out.print(      "                                  <td>"+dname+"</td>");
                out.print(      "                                  <td>");
                out.print(      " <a href=\"javascript:void(0)\" onclick=\"del("+deptno+")\" >删除</a>    ");
                out.print(      "                                     <a href='"+contextPath+"/dept/edit?deptno="+deptno+"'>修改</a>");
                out.print(      "                                      <a href='"+contextPath+"/dept/detail?deptno="+deptno+"'>详情</a>");
                out.print(      "                                  </td>");
                out.print(      "                              </tr>");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        out.print(      "                          </table>");
        out.print(      "                          <hr >");
        out.print(      "                          <a href='"+contextPath+"/add.html'>新增部门</a>");
        out.print(      "                      </body>");
        out.print(      "                  </html>                                                                       ");
    }
}
