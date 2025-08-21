package university.management.system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleCheck {

    public static boolean hasRole(int userId, String roleName) {
        String query = "SELECT r.name FROM user_roles ur " +
                       "JOIN roles r ON ur.role_id = r.id " +
                       "WHERE ur.user_id = ?";

        try {
            Conn c = new Conn();                  // use your custom Conn class
            Connection conn = c.c;                // actual JDBC connection

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("name").equalsIgnoreCase(roleName)) {
                    rs.close();
                    stmt.close();
                    conn.close();
                    return true;
                }
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
