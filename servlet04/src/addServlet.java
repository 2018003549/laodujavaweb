import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String deptno = req.getParameter("deptno");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        int count=0;
        try {
            connection = DBUtils.getConnection();
            //开启事务(关闭自动提交)
            connection.setAutoCommit(false);
            String sql="insert into dept values (?,?,?)";//占位符
            preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1,deptno);//给占位符赋值
            preparedStatement.setString(2,dname);//给占位符赋值
            preparedStatement.setString(3,loc);//给占位符赋值
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
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else{
            resp.sendRedirect(req.getContextPath()+"/error.html");
        }
    }
}
