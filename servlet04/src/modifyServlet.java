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

public class modifyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //获取数据
        String deptno = req.getParameter("deptno");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");
        PrintWriter out = resp.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
        int count=0;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();
            //编写sql，获取预编译数据库操作对象
            String sql = "update  dept set dname=?,loc=? where deptno='"+deptno+"'";
            ps = connection.prepareStatement(sql);
            ps.setString(1,dname);
            ps.setString(2,loc);
            //执行sql
             count = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(connection,ps,null);
        }
        if(count>0){
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else{
            resp.sendRedirect(req.getContextPath()+"/error.html");
        }
    }
}
