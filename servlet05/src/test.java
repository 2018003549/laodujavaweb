import javax.servlet.annotation.WebServlet;

public class test {
    public static void main(String[] args) throws ClassNotFoundException {
        //获取类class对象
        Class<?> helloServlet = Class.forName("HelloServlet");
        //获取这个类上面的注解对象（先判断有没有该对象）
        if (helloServlet.isAnnotationPresent(WebServlet.class)) {
            //获取类上的注解对象
            WebServlet annotation = helloServlet.getAnnotation(WebServlet.class);
            String[] value = annotation.value();
            String[] urlPatterns = annotation.urlPatterns();
            for (String s : value) {
                System.out.println(s);
            }
            System.out.println("-----------");
            for (String urlPattern : urlPatterns) {
                System.out.println(urlPattern);
            }
        }
    }
}
