import javax.servlet.*;
import java.io.IOException;

public class ContextServlert extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        ServletContext application = this.getServletContext();
        application.log("日志信息测试");
    }
}
