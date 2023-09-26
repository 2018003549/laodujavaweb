import javax.servlet.*;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class ConfigServlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.setContentType("text/html");
        PrintWriter writer = servletResponse.getWriter();
        javax.servlet.ServletConfig config = getServletConfig();
        writer.println(config+"<br>");
        writer.println(config.getServletName()+"<br>");
        Enumeration<String> initParameterNames = config.getInitParameterNames();
        while(initParameterNames.hasMoreElements()){
            String element = initParameterNames.nextElement();
            String initParameter = config.getInitParameter(element);
            writer.println(element+"  "+initParameter+"<br>");
        }
    }
}
