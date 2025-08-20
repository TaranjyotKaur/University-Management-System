package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import university.management.system.Login;

public class TeacherDashboard extends JFrame {
    public TeacherDashboard() {
        setTitle("Teacher Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome, Teacher!", SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(20f));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login().setVisible(true);
            }
        });

        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        add(logoutBtn, BorderLayout.SOUTH);
    }
}
