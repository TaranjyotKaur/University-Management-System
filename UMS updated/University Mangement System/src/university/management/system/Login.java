package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.PreparedStatement;
import jakarta.mail.MessagingException;

public class Login extends JFrame implements ActionListener {

    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin, btnCancel;

    private int attempts = 0;
    private static final int MAX_ATTEMPTS = 5;

    public Login() {
        setTitle("Login");
        setSize(420, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 10, 10, 10);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; add(new JLabel("Username:"), g);
        tfUsername = new JTextField(18);
        g.gridx = 1; add(tfUsername, g);

        g.gridx = 0; g.gridy = 1; add(new JLabel("Password:"), g);
        pfPassword = new JPasswordField(18);
        pfPassword.setTransferHandler(null); // disable copy/paste
        g.gridx = 1; add(pfPassword, g);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(this);
        g.gridx = 0; g.gridy = 2; add(btnLogin, g);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> System.exit(0));
        g.gridx = 1; g.gridy = 2; add(btnCancel, g);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (attempts >= MAX_ATTEMPTS) {
            JOptionPane.showMessageDialog(this, "Too many failed attempts. Application will exit.");
            System.exit(0);
        }

        String username = SecurityUtil.safeTrim(tfUsername.getText());
        String password = new String(pfPassword.getPassword());

        // Basic input validation
        if (!SecurityUtil.validUsername(username)) {
            JOptionPane.showMessageDialog(this, "Invalid username format.");
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.");
            return;
        }

        // Authenticate (also updates DB counters)
        List<String> roles = authenticateAndUpdate(username, password);

        if (roles.isEmpty()) {
            attempts++;
            int remaining = MAX_ATTEMPTS - attempts;
            JOptionPane.showMessageDialog(this, "Invalid username or password. Attempts left: " + remaining);
        } else {
            // 2FA — send OTP
            String otp = OTPService.generateOTP();

            String toEmail = fetchUserEmail(username);
            if (toEmail == null || toEmail.isEmpty()) {
                // Ask once (so you can test now even if DB has no email column)
                toEmail = JOptionPane.showInputDialog(this,
                    "Enter email to receive OTP (temporary for this login):");
            }

            boolean otpOk = false;
            try {
                if (toEmail != null && !toEmail.isEmpty() && SecurityUtil.validEmail(toEmail)) {
                    OTPService.sendOTP(toEmail, otp);
                } else {
                    JOptionPane.showMessageDialog(this, "Skipping email send (no valid email). Showing OTP dialog for testing.");
                }
                String input = JOptionPane.showInputDialog(this, "Enter the OTP sent to your email:\n" +
                        (toEmail == null ? "(Test OTP: " + otp + ")" : "")); // test fallback display
                otpOk = (input != null && input.trim().equals(otp));
            } catch (MessagingException me) {
                me.printStackTrace();
                String input = JOptionPane.showInputDialog(this, "Email send failed. Enter OTP (test code: " + otp + "):");
                otpOk = (input != null && input.trim().equals(otp));
            }

            if (!otpOk) {
                JOptionPane.showMessageDialog(this, "Invalid OTP. Login cancelled.");
                return;
            }

            JOptionPane.showMessageDialog(this, "Login successful. Roles: " + roles);

            // Redirect based on role priority
            if (roles.contains("ADMIN"))       new Project("ADMIN");
            else if (roles.contains("TEACHER")) new Project("TEACHER");
            else                                 new Project("STUDENT");

            dispose();
        }
    }

    private List<String> authenticateAndUpdate(String username, String password) {
        List<String> roles = new ArrayList<>();
        try (Conn conn = new Conn()) {
            // Check lock
            PreparedStatement chk = conn.prepare(
                "SELECT id, password_hash, failed_attempts, account_locked " +
                "FROM users WHERE username=?"
            );
            chk.setString(1, username);
            ResultSet r1 = chk.executeQuery();

            if (!r1.next()) {
                r1.close(); chk.close();
                return roles; // no such user
            }

            int userId = r1.getInt("id");
            String hash = r1.getString("password_hash");
            int fails   = r1.getInt("failed_attempts");
            boolean locked = r1.getBoolean("account_locked");
            r1.close(); chk.close();

            if (locked || fails >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(this, "Account locked. Contact admin.");
                return roles;
            }

            if (!BCrypt.checkpw(password, hash)) {
                PreparedStatement inc = conn.prepare(
                    "UPDATE users SET failed_attempts = failed_attempts + 1, " +
                    "account_locked = (failed_attempts + 1 >= ?) WHERE id=?"
                );
                inc.setInt(1, MAX_ATTEMPTS);
                inc.setInt(2, userId);
                inc.executeUpdate();
                inc.close();
                return roles;
            }

            // Reset counters on success
            PreparedStatement reset = conn.prepare(
                "UPDATE users SET failed_attempts = 0, account_locked = 0 WHERE id=?"
            );
            reset.setInt(1, userId);
            reset.executeUpdate();
            reset.close();

            // Fetch roles
            PreparedStatement pst = conn.prepare(
                "SELECT r.name AS role " +
                "FROM user_roles ur JOIN roles r ON ur.role_id=r.id " +
                "WHERE ur.user_id=?"
            );
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) roles.add(rs.getString("role").toUpperCase());
            rs.close(); pst.close();

            if (roles.isEmpty()) {
                // Fallback if you also have a `role` column directly in users
                PreparedStatement pst2 = conn.prepare("SELECT role FROM users WHERE id=?");
                pst2.setInt(1, userId);
                ResultSet rs2 = pst2.executeQuery();
                if (rs2.next() && rs2.getString("role") != null)
                    roles.add(rs2.getString("role").toUpperCase());
                rs2.close(); pst2.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return roles;
    }

    private String fetchUserEmail(String username) {
        try (Conn conn = new Conn()) {
            // Only works if your users table has an email column
            PreparedStatement pst = conn.prepare(
                "SELECT email FROM users WHERE username=?"
            );
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            String email = null;
            if (rs.next()) email = rs.getString("email");
            rs.close(); pst.close();
            return email;
        } catch (Exception e) {
            return null; // fine if you don't have email column yet
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }

    // tiny helper to enable try-with-resources with Conn
    private static class Conn extends university.management.system.Conn implements AutoCloseable {}
}
