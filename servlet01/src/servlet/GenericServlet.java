package servlet;
import javax.servlet.*;
import java.io.IOException;
public class GenericServlet implements Servlet{
    private ServletConfig config;
    @Override
    public final void init(ServletConfig servletConfig) throws ServletException {
        this.config=servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
