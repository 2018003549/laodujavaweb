import javaBean.Dept;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/dept/*")
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String path=servletPath+pathInfo;
        if ("/dept/list".equals(path)) {
            doList(request, response);
        } else if ("/dept/delete".equals(path)) {
            DoDelete(request, response);
        } else if ("/dept/save".equals(path)) {
            doSave(request, response);
        } else if ("/dept/modify".equals(path)) {
            doModify(request, response);
        } else if ("/dept/detail".equals(path)) {
            doDetail(request, response);
        }
    }

    private void doModify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        //获取数据
        String deptno = req.getParameter("deptno");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");
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

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //获取部门编号
        String deptno = request.getParameter("deptno");
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
            Dept dept = null;
            //处理结果集,因为只查询出一条数据，用if即可
            if (rs.next()) {
                String no = rs.getString("deptno");
                String name = rs.getString("dname");
                String loc = rs.getString("loc");
                dept = new Dept(no, name, loc);
                request.setAttribute("dept", dept);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, rs);
        }
        String path = request.getParameter("path");
        request.getRequestDispatcher("/" + path + ".jsp").forward(request, response);
    }

    private void DoDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            String deptno = request.getParameter("deptno");
            connection = DBUtils.getConnection();
            connection.setAutoCommit(false);
            String sql = "delete from  dept where deptno=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, deptno);
            count = ps.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, null);
        }
        if (count > 0) {
            response.sendRedirect(request.getContextPath() + "/dept/list");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
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

    private void doList(HttpServletRequest request, HttpServletResponse response) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();
            String sql = "select deptno,dname,loc from dept";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            Dept dept = null;
            ArrayList<Dept> deptArrayList = new ArrayList<>();
            while (rs.next()) {
                String deptno = rs.getString("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                dept = new Dept(deptno, dname, loc);
                deptArrayList.add(dept);
            }
            request.setAttribute("deptList", deptArrayList);
            request.getRequestDispatcher("/list.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtils.close(connection, ps, rs);
        }
    }
}
