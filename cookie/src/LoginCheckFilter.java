import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String path;
        if (pathInfo != null) {
             path = servletPath + pathInfo;
        }
        path = servletPath;
        //先获取session对象，如果没有session对象就说明没有用户登录
        HttpSession session = request.getSession(false);//没有session对象时无法创建新session对象
        if ((session != null && session.getAttribute("user") != null)
                || "/index.jsp".equals(path) || "/login.jsp".equals(path) ||
                "/checkCookie".equals(path) || "/login".equals(path)) {//仅仅判断session是否存在不行，因为访问jsp时会创建内置对象session
            chain.doFilter(request, response);
        } else {
            //登录失败返回登录界面
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
