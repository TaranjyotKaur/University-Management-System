package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

public class Register extends JFrame implements ActionListener {

    private JTextField tfUsername, tfEmail; // email optional if not in schema
    private JPasswordField pfPassword;
    private JComboBox<String> roleBox;
    private JButton btnRegister;

    public Register() {
        setTitle("Register User");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 10, 8, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        // Username
        g.gridx = 0; g.gridy = 0; panel.add(new JLabel("Username:"), g);
        tfUsername = new JTextField(18);
        g.gridx = 1; panel.add(tfUsername, g);

        // Password
        g.gridx = 0; g.gridy = 1; panel.add(new JLabel("Password:"), g);
        pfPassword = new JPasswordField(18);
        pfPassword.setTransferHandler(null); // disable copy/paste
        g.gridx = 1; panel.add(pfPassword, g);

        // Email (optional in DB; useful for OTP)
        g.gridx = 0; g.gridy = 2; panel.add(new JLabel("Email (for OTP):"), g);
        tfEmail = new JTextField(18);
        g.gridx = 1; panel.add(tfEmail, g);

        // Role
        g.gridx = 0; g.gridy = 3; panel.add(new JLabel("Role:"), g);
        roleBox = new JComboBox<>(new String[]{"ADMIN", "TEACHER", "STUDENT"});
        g.gridx = 1; panel.add(roleBox, g);

        // Register button
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(this);
        g.gridx = 0; g.gridy = 4; g.gridwidth = 2; panel.add(btnRegister, g);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = SecurityUtil.safeTrim(tfUsername.getText());
        String email    = SecurityUtil.safeTrim(tfEmail.getText());
        char[] pw       = pfPassword.getPassword();
        String role     = String.valueOf(roleBox.getSelectedItem());

        if (!SecurityUtil.validUsername(username)) {
            JOptionPane.showMessageDialog(this, "Username must be 3–32 chars [A-Z,a-z,0-9,_.-].");
            return;
        }
        if (!SecurityUtil.strongPassword(pw)) {
            JOptionPane.showMessageDialog(this, "Password must be ≥8 chars with letters and numbers.");
            return;
        }
        if (!email.isEmpty() && !SecurityUtil.validEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return;
        }

        String hashed = BCrypt.hashpw(new String(pw), BCrypt.gensalt());

        try (Conn conn = new Conn()) { // use try-with-resources via small helper below
            // If your users table has an email column:
            // String sql = "INSERT INTO users (username, password_hash, email) VALUES (?, ?, ?)";
            // Otherwise (no email column):
            String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

            PreparedStatement pst = conn.prepare(sql);
            pst.setString(1, username);
            pst.setString(2, hashed);
            // If you *do* have an email column, uncomment:
            // pst.setString(3, email.isEmpty() ? null : email);

            pst.executeUpdate();
            pst.close();

            // If you’re using user_roles mapping table:
            PreparedStatement link = conn.prepare(
                "INSERT INTO user_roles (user_id, role_id) " +
                "SELECT u.id, r.id FROM users u, roles r WHERE u.username=? AND r.name=?"
            );
            link.setString(1, username);
            link.setString(2, role);
            link.executeUpdate();
            link.close();

            JOptionPane.showMessageDialog(this, "User registered successfully!");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user.");
        }
    }

    // tiny helper to enable try-with-resources with Conn
    private static class Conn extends university.management.system.Conn implements AutoCloseable {}
}
