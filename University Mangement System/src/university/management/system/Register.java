package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.PreparedStatement;
import org.mindrot.jbcrypt.BCrypt;

public class Register extends JFrame implements ActionListener {

    JTextField tfUsername;
    JPasswordField pfPassword;
    JComboBox<String> roleBox;
    JButton btnRegister;

    public Register() {
        setTitle("Register User");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        tfUsername = new JTextField(15);
        gbc.gridx = 1;
        panel.add(tfUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        pfPassword = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(pfPassword, gbc);

        // Role
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);
        roleBox = new JComboBox<>(new String[]{"ADMIN", "TEACHER", "STUDENT"});
        gbc.gridx = 1;
        panel.add(roleBox, gbc);

        // Register button
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(this);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnRegister, gbc);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword());
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        try {
            Conn conn = new Conn();
            PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)"
            );
            pst.setString(1, username);
            pst.setString(2, hashed);
            pst.setString(3, role);

            pst.executeUpdate();

            pst.close();
            conn.close();

            JOptionPane.showMessageDialog(this, "User registered successfully!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering user.");
        }
    }

    public static void main(String[] args) {
        new Register();
    }
}
