package javaBean;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Objects;

public class User implements HttpSessionBindingListener {

int a;
public void doGood(){
    System.out.println();
}

    public void service(){
        System.out.println("dada");
    }
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        //先通过event对象获取count，因为是所有用户数量，所以去application域中取
        ServletContext application = event.getSession().getServletContext();
        Integer count = (Integer) application.getAttribute("count");
        if(count==null){
            //说明当前只有一个用户登录,要把count存入域中
            application.setAttribute("count",1);
        }else {
            count++;
            application.setAttribute("count",count);
        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        //先通过event对象获取count，因为是所有用户数量，所以去application域中取
        ServletContext application = event.getSession().getServletContext();
        Integer count = (Integer) application.getAttribute("count");
        count--;
        application.setAttribute("count",count);

    }
    String username;
    String password;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
