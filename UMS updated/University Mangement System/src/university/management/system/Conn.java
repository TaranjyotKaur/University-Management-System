package university.management.system;

import java.sql.*; 
import java.sql.PreparedStatement;

public class Conn {
    private static final String URL =
        "jdbc:mysql://localhost:3306/universitymanagementsystem"
        + "?useSSL=true&requireSSL=false&verifyServerCertificate=false"
        + "&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "yourpassword"; // TODO: set

    public Connection c;
    public Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(URL, USER, PASS);
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepare(String sql) throws SQLException {
        return c.prepareStatement(sql);
    }

    public void close() {
        try {
            if (s != null && !s.isClosed()) s.close();
            if (c != null && !c.isClosed()) c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
