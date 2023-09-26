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

@WebServlet({"/login","/logout"})
public class loginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if("/login".equals(servletPath)){
            doLogin(req,resp);
        }else if("/logout".equals(servletPath)){
            doLogout(req,resp);
        }
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect(req.getContextPath()+"/login.jsp");
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
            ps.setString(1,username);
            ps.setString(2,password);
            rs=ps.executeQuery();
            if(rs.next()){
                //如果有该用户就返回一条数据
                success=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (success) {
            //如果登录成功，则创建一个session对象，保持用户登录成功的会话状态
            HttpSession session = req.getSession();
            session.setAttribute("success","成功");
            session.setAttribute("username",username);
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else{
            resp.sendRedirect(req.getContextPath()+"/error.jsp");
        }
    }

}
