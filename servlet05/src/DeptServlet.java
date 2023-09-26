import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet("/dept/*")//请求路径以/dept开始的就执行这个servlet
public class DeptServlet extends HttpServlet {
    //模板方法,重写service（因为不知道前端发送的是get还是post请求）
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取servlet path（不带项目名）
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String path=servletPath+pathInfo;
        //一个路径对应一个功能
        if ("/dept/list".equals(path)) {
            doList(request, response);
        } else if ("/dept/save".equals(path)) {
            doSave(request, response);
        } else if ("/dept/edit".equals(path)) {
            doEdit(request, response);
        } else if ("/dept/detail".equals(path)) {
            doDetail(request, response);
        } else if ("/dept/delete".equals(path)) {
            doDel(request, response);
        } else if ("/dept/modify".equals(path)) {
            doModify(request, response);
        } else {
            System.out.println("路径不存在");
        }
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        out.print(      "                  </html>     ");
    }

    private void doSave(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String deptno = request.getParameter("deptno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = DBUtils.getConnection();
            //开启事务(关闭自动提交)
            connection.setAutoCommit(false);
            String sql = "insert into dept values (?,?,?)";//占位符
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, deptno);//给占位符赋值
            preparedStatement.setString(2, dname);//给占位符赋值
            preparedStatement.setString(3, loc);//给占位符赋值
            count = preparedStatement.executeUpdate();//返回值是影响了多少条记录
            connection.commit();
        } catch (SQLException throwables) {
            if (connection != null) {
                try {
                    //出现异常就回滚
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throwables.printStackTrace();
        } finally {
            DBUtils.close(connection, preparedStatement, null);
        }
        if (count > 0) {
            resp.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            resp.sendRedirect(request.getContextPath() + "/error.html");
        }
    }


    private void doEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //获取部门编号
        String deptno = request.getParameter("deptno");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();
            //编写sql，获取预编译数据库操作对象
            String sql = "select deptno,dname,loc from dept where deptno='" + deptno + "'";
            ps = connection.prepareStatement(sql);
            //执行sql
            rs = ps.executeQuery();
            int i = 0;
            //处理结果集
            while (rs.next()) {
                String no = rs.getString("deptno");
                String name = rs.getString("dname");
                String loc = rs.getString("loc");
                out.println(no + "<br>" + name + "<br>" + loc);
            }
            out.println("<input type=\"button\" value=\"后退\" onclick=\"window.history.back()\"/>");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, rs);
        }
    }

    private void doDel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先获取部门编号
        String deptno = req.getParameter("deptno");
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        int count=0;
        try {
            connection = DBUtils.getConnection();
            //开启事务(关闭自动提交)
            connection.setAutoCommit(false);
            String sql="delete from dept where deptno=?";//占位符
            preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1,deptno);//给占位符赋值
            count = preparedStatement.executeUpdate();//返回值是影响了多少条记录
            connection.commit();
        } catch (SQLException throwables) {
            if(connection!=null){
                try {
                    //出现异常就回滚
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throwables.printStackTrace();
        }finally {
            DBUtils.close(connection,preparedStatement,null);
        }
        if (count>0) {
            //删除成功
            //仍然跳转到部门列表页面
            //部门列表页面的显示需要执行另一个Servlet。怎么办？转发。
            req.getRequestDispatcher("/dept/list").forward(req, resp);
        }else{
            // 删除失败
            req.getRequestDispatcher("/error.html").forward(req, resp);
        }
    }

    private void doModify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //获取数据
        String deptno = req.getParameter("deptno");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");
        PrintWriter out = resp.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
        int count = 0;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();
            //编写sql，获取预编译数据库操作对象
            String sql = "update  dept set dname=?,loc=? where deptno='" + deptno + "'";
            ps = connection.prepareStatement(sql);
            ps.setString(1, dname);
            ps.setString(2, loc);
            //执行sql
            count = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, null);
        }
        if (count > 0) {
            resp.sendRedirect(req.getContextPath() + "/dept/list");
        } else {
            resp.sendRedirect(req.getContextPath() + "/error.html");
        }
    }
}


