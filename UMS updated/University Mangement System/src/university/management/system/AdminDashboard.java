package university.management.system;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    private Image backgroundImage;

    public AdminDashboard() {
        setTitle("ADMIN Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load background image
        backgroundImage = new ImageIcon(getClass().getResource("/icons/dashboard.jpg")).getImage();

        // Custom JPanel with background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        /* ---------------- STUDENT MENU ---------------- */
        JMenu studentMenu = new JMenu("Student");

        JMenuItem addStudent = new JMenuItem("Add Student");
        addStudent.addActionListener(e -> new AddStudent().setVisible(true));

        JMenuItem updateStudent = new JMenuItem("Update Student");
        updateStudent.addActionListener(e -> new UpdateStudent().setVisible(true));

        JMenuItem studentDetails = new JMenuItem("Student Details");
        studentDetails.addActionListener(e -> new StudentDetails().setVisible(true));

        JMenuItem studentLeave = new JMenuItem("Student Leave");
        studentLeave.addActionListener(e -> new StudentLeave().setVisible(true));

        studentMenu.add(addStudent);
        studentMenu.add(updateStudent);
        studentMenu.add(studentDetails);
        studentMenu.add(studentLeave);

        /* ---------------- TEACHER MENU ---------------- */
        JMenu teacherMenu = new JMenu("Teacher");

        JMenuItem addTeacher = new JMenuItem("Add Teacher");
        addTeacher.addActionListener(e -> new AddTeacher().setVisible(true));

        JMenuItem updateTeacher = new JMenuItem("Update Teacher");
        updateTeacher.addActionListener(e -> new UpdateTeacher().setVisible(true));

        JMenuItem teacherDetails = new JMenuItem("Teacher Details");
        teacherDetails.addActionListener(e -> new TeacherDetails().setVisible(true));

        JMenuItem teacherLeave = new JMenuItem("Teacher Leave");
        teacherLeave.addActionListener(e -> new TeacherLeave().setVisible(true));

        teacherMenu.add(addTeacher);
        teacherMenu.add(updateTeacher);
        teacherMenu.add(teacherDetails);
        teacherMenu.add(teacherLeave);

        /* ---------------- FEE MENU ---------------- */
        JMenu feeMenu = new JMenu("Fee");

        JMenuItem feeStructure = new JMenuItem("Fee Structure");
        feeStructure.addActionListener(e -> new FeeStructure().setVisible(true));

        feeMenu.add(feeStructure);

        /* ---------------- EXIT MENU ---------------- */
        JMenu exitMenu = new JMenu("Exit");
        JMenuItem logout = new JMenuItem("Logout");
        logout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
        exitMenu.add(logout);

        // Add Menus to MenuBar
        menuBar.add(studentMenu);
        menuBar.add(teacherMenu);
        menuBar.add(feeMenu);
        menuBar.add(exitMenu);

        setJMenuBar(menuBar);

        // Welcome Label
        JLabel welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.BLACK);

        backgroundPanel.add(welcomeLabel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
