package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project extends JFrame implements ActionListener {
    private final String role;
    private final Timer idleTimer;

    JMenuBar mb;
    JMenu studentMenu, teacherMenu, feeMenu, exitMenu;
    JMenuItem addStudent, updateStudent, studentDetails, studentLeave;
    JMenuItem addTeacher, updateTeacher, teacherDetails, teacherLeave;
    JMenuItem feeStructure, studentFeeForm;
    JMenuItem exitItem;

    public Project(String role) {
        this.role = role;
        setTitle(role + " Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Menu bar
        mb = new JMenuBar();

        studentMenu = new JMenu("Student");
        addStudent = new JMenuItem("Add Student");
        updateStudent = new JMenuItem("Update Student");
        studentDetails = new JMenuItem("Student Details");
        studentLeave = new JMenuItem("Student Leave");
        for (JMenuItem it : new JMenuItem[]{addStudent, updateStudent, studentDetails, studentLeave}) it.addActionListener(this);
        studentMenu.add(addStudent); studentMenu.add(updateStudent);
        studentMenu.add(studentDetails); studentMenu.add(studentLeave);

        teacherMenu = new JMenu("Teacher");
        addTeacher = new JMenuItem("Add Teacher");
        updateTeacher = new JMenuItem("Update Teacher");
        teacherDetails = new JMenuItem("Teacher Details");
        teacherLeave = new JMenuItem("Teacher Leave");
        for (JMenuItem it : new JMenuItem[]{addTeacher, updateTeacher, teacherDetails, teacherLeave}) it.addActionListener(this);
        teacherMenu.add(addTeacher); teacherMenu.add(updateTeacher);
        teacherMenu.add(teacherDetails); teacherMenu.add(teacherLeave);

        feeMenu = new JMenu("Fee");
        feeStructure = new JMenuItem("Fee Structure");
        studentFeeForm = new JMenuItem("Student Fee Form");
        feeStructure.addActionListener(this);
        studentFeeForm.addActionListener(this);
        feeMenu.add(feeStructure); feeMenu.add(studentFeeForm);

        exitMenu = new JMenu("Exit");
        exitItem = new JMenuItem("Logout / Exit");
        exitItem.addActionListener(this);
        exitMenu.add(exitItem);

        mb.add(studentMenu); mb.add(teacherMenu); mb.add(feeMenu); mb.add(exitMenu);
        setJMenuBar(mb);

        // Simple background + welcome
        JLabel welcome = new JLabel("Welcome, " + role, SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        add(welcome);

        applyRoleRestrictions();

        // Auto-logout after 5 minutes idle
        idleTimer = new Timer(5 * 60 * 1000, e -> {
            JOptionPane.showMessageDialog(this, "Session expired due to inactivity.");
            dispose();
            new Login().setVisible(true);
        });
        idleTimer.setRepeats(false);
        idleTimer.start();

        // Reset timer on activity
        addMouseMotionListener(new MouseMotionAdapter() { 
            @Override 
            public void mouseMoved(MouseEvent e) { idleTimer.restart(); }
        });
        addKeyListener(new KeyAdapter() { 
            @Override 
            public void keyPressed(KeyEvent e) { idleTimer.restart(); }
        });

        setVisible(true);
    }

    private void applyRoleRestrictions() {
        if (role.equalsIgnoreCase("STUDENT")) {
            addStudent.setEnabled(false);
            updateStudent.setEnabled(false);
            addTeacher.setEnabled(false);
            updateTeacher.setEnabled(false);
            teacherDetails.setEnabled(false);
            teacherLeave.setEnabled(false);
        } else if (role.equalsIgnoreCase("TEACHER")) {
            addStudent.setEnabled(false);
            updateStudent.setEnabled(false);
            addTeacher.setEnabled(false);
            updateTeacher.setEnabled(false);
            feeStructure.setEnabled(false);
            studentFeeForm.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "Add Student":
                new AddStudent().setVisible(true);
                break;
            case "Update Student":
                new UpdateStudent().setVisible(true);
                break;
            case "Student Details":
                new StudentDetails().setVisible(true);
                break;
            case "Student Leave":
                new StudentLeave().setVisible(true);
                break;
            case "Add Teacher":
                new AddTeacher().setVisible(true);
                break;
            case "Update Teacher":
                new UpdateTeacher().setVisible(true);
                break;
            case "Teacher Details":
                new TeacherDetails().setVisible(true);
                break;
            case "Teacher Leave":
                new TeacherLeave().setVisible(true);
                break;
            case "Fee Structure":
                new FeeStructure().setVisible(true);
                break;
            case "Student Fee Form":
                new StudentFeeForm().setVisible(true);
                break;
            case "Logout / Exit":
                dispose();
                new Login().setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, cmd + " clicked (no screen implemented yet).");
        }
    }

    public static void main(String[] args) {
        new Project("ADMIN");
    }
}
