import javaBean.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet("/checkCookie")
public class check extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cookie[] cookies = req.getCookies();
        String username = null;
        String password = null;
        Boolean success = false;
        try {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    //用户名和密码是由两个cookie存储的
                    if ("username".equals(cookie.getName())) {
                        username = cookie.getValue();
                    } else if ("password".equals(cookie.getName())) {
                        password = cookie.getValue();
                    }
                }
            }
            if (username != null && password != null) {
                //判断有用户名和密码信息再进行比对
                conn = DBUtils.getConnection();
                String sql = "select * from tb_user where username=? and password=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                //如果用户名和密码正确，则有查询结过
                if (rs.next()) {
                    success = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DBUtils.close(conn,ps,rs);
        }
        if (success) {
            //如果登录成功，则创建一个session对象，保持用户登录成功的会话状态
            HttpSession session = req.getSession();
            session.setAttribute("user",new User(username,password));
            resp.sendRedirect(req.getContextPath()+"/dept/list");
        }else{
            resp.sendRedirect(req.getContextPath()+"/login.jsp");
        }
    }
}
