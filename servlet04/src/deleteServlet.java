import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class deleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
}
