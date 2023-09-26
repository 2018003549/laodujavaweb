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

public class detailServlet extends HttpServlet {
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
            //编写sql，获取预编译数据库操作对象
            String sql = "select deptno,dname,loc from dept where deptno='"+deptno+"'";
            ps = connection.prepareStatement(sql);
            //执行sql
            rs = ps.executeQuery();
            int i=0;
            //处理结果集
            while(rs.next()){
                String no = rs.getString("deptno");
                String name = rs.getString("dname");
                String loc = rs.getString("loc");
                out.println(no+"<br>"+name+"<br>"+loc);
            }
            out.println("<input type=\"button\" value=\"后退\" onclick=\"window.history.back()\"/>");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(connection,ps,rs);
        }
    }
}
