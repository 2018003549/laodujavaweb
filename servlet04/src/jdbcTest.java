import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class jdbcTest {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBUtils.getConnection();
        String sql = "select deptno,dname,loc from dept";
        PreparedStatement ps = connection.prepareStatement(sql);
        //执行sql
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            System.out.println(1);
            String deptno = rs.getString("deptno");//根据查询的字段名获取信息，如果起别名则改成别名
            String dname = rs.getString("dname");
            String loc = rs.getString("loc");
            System.out.println(deptno);
        }
    }
}
