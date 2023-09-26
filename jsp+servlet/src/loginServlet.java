import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet("/login")
public class loginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Boolean success=false;
        try {
            conn = DBUtils.getConnection();
            String sql= "select * from tb_user where username=? and password=?";
            ps = conn.prepareStatement(sql);
            rs=ps.executeQuery();
            if(rs.next()){
                //如果有该用户就返回一条数据
                success=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (success) {
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else{
            resp.sendRedirect(req.getContextPath()+"/error.jsp");
        }


    }
}
