package portfolio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel no_of_courses;
    private JLabel no_of_teachers;
    private JLabel no_of_students;
    private final String url = "jdbc:mysql://localhost:3306/coursemanagement";
    private final String user = "root";
    private final String dbPassword = "12345";
    private JTextArea activityTextArea;
    private JLabel nameLabel;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard frame = new Dashboard();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Dashboard() {
        this("User Mode", null); // Default to "User Mode" and no reference to the Settings frame
        setResizable(false);
    }

    public Dashboard(String userMode, Settings settingsFrame) {
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\logo.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 828, 499);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(new Color(128, 128, 128));
        desktopPane.setBounds(0, 0, 204, 460);
        contentPane.add(desktopPane);

        JLabel cms_title = new JLabel("Course Management System");
        cms_title.setForeground(new Color(0, 0, 0));
        cms_title.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        cms_title.setBounds(10, 100, 194, 14);
        desktopPane.add(cms_title);

        JButton courses_btn = new JButton("Courses");
        courses_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCoursesPanel();
            }

            private void openCoursesPanel() {
                Courses coursesPanel = new Courses(userMode);
                coursesPanel.setVisible(true);
                coursesPanel.setLocationRelativeTo(null);
            }

        });
        courses_btn.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\course.png"));
        courses_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        courses_btn.setBounds(26, 184, 140, 36);
        desktopPane.add(courses_btn);

        JButton tutors_btn = new JButton("Tutors");
        tutors_btn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                openTutorsPage();
            }

            private void openTutorsPage() {
                Teachers tutorPanel = new Teachers(userMode);
                tutorPanel.setVisible(true);
                tutorPanel.setLocationRelativeTo(null);
            }
        });
        tutors_btn.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\teacher.png"));
        tutors_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        tutors_btn.setBounds(26, 231, 140, 35);
        desktopPane.add(tutors_btn);

        JButton students_btn = new JButton("Students");
        students_btn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {

                openStudentPanel();
            }

            private void openStudentPanel() {
                Students studentPanel = new Students(userMode);
                studentPanel.setVisible(true);
                studentPanel.setLocationRelativeTo(null);
            }
        });
        students_btn.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\student.png"));
        students_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        students_btn.setBounds(26, 277, 140, 38);
        desktopPane.add(students_btn);

        JButton mail_btn = new JButton("Mail");
        mail_btn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Open the default web browser with the Gmail URL
                    Desktop.getDesktop().browse(new URI("https://mail.google.com/"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace(); // Handle any potential exceptions
                }
            }
        });

        mail_btn.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\mail.png"));
        mail_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        mail_btn.setBounds(26, 326, 140, 38);
        desktopPane.add(mail_btn);

        JButton settings_btn = new JButton("Settings");
        settings_btn.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }

            private void openSettings() {
                Settings settingsFrame = new Settings(userMode, Dashboard.this);
                settingsFrame.setVisible(true);
                settingsFrame.setLocationRelativeTo(null);
            }
        });

        settings_btn.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\settings.png"));
        settings_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        settings_btn.setBounds(26, 379, 140, 35);
        desktopPane.add(settings_btn);

        JLabel herald_logo = new JLabel("");
        herald_logo.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\herald.jpg"));
        herald_logo.setBounds(26, 11, 157, 84);
        desktopPane.add(herald_logo);

        JButton courses_btn_1 = new JButton("Dashboard");
        courses_btn_1.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        courses_btn_1.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\dashboard.png"));
        courses_btn_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        courses_btn_1.setBounds(26, 139, 140, 34);
        desktopPane.add(courses_btn_1);

        JDesktopPane desktopPane_1 = new JDesktopPane();
        desktopPane_1.setBackground(new Color(128, 128, 128));
        desktopPane_1.setBounds(206, 0, 600, 130);
        contentPane.add(desktopPane_1);

        JLabel title = new JLabel("Dashboard");
        title.setBounds(256, 0, 113, 35);
        desktopPane_1.add(title);
        title.setBackground(new Color(255, 255, 255));
        title.setFont(new Font("Trebuchet MS", Font.BOLD, 22));

        JDesktopPane course_pane = new JDesktopPane();
        course_pane.setBorder(UIManager.getBorder("CheckBox.border"));
        course_pane.setBounds(231, 39, 94, 77);
        desktopPane_1.add(course_pane);
        course_pane.setBackground(new Color(192, 192, 192));

        JLabel total_course = new JLabel("   Total Courses");
        total_course.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
        total_course.setBounds(0, 0, 74, 25);
        course_pane.add(total_course);

        no_of_courses = new JLabel("0");
        no_of_courses.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
        no_of_courses.setBounds(20, 27, 64, 39);
        course_pane.add(no_of_courses);

        JDesktopPane teachers_pane = new JDesktopPane();
        teachers_pane.setBorder(UIManager.getBorder("RadioButton.border"));
        teachers_pane.setBackground(new Color(192, 192, 192));
        teachers_pane.setBounds(355, 39, 94, 77);
        desktopPane_1.add(teachers_pane);

        JLabel total_teacher = new JLabel("   Total Teachers");
        total_teacher.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
        total_teacher.setBounds(0, 0, 84, 25);
        teachers_pane.add(total_teacher);

        no_of_teachers = new JLabel("0");
        no_of_teachers.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
        no_of_teachers.setBounds(20, 27, 64, 39);
        teachers_pane.add(no_of_teachers);

        JDesktopPane students_pane = new JDesktopPane();
        students_pane.setBorder(UIManager.getBorder("RadioButton.border"));
        students_pane.setBackground(new Color(192, 192, 192));
        students_pane.setBounds(482, 39, 94, 77);
        desktopPane_1.add(students_pane);

        JLabel total_students = new JLabel("   Total Students");
        total_students.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
        total_students.setBounds(0, 0, 84, 25);
        students_pane.add(total_students);

        no_of_students = new JLabel("0");
        no_of_students.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
        no_of_students.setBounds(20, 27, 64, 39);
        students_pane.add(no_of_students);

        JDesktopPane course_pane_1 = new JDesktopPane();
        course_pane_1.setBorder(UIManager.getBorder("CheckBox.border"));
        course_pane_1.setBackground(new Color(192, 192, 192));
        course_pane_1.setBounds(119, 39, 87, 77);
        desktopPane_1.add(course_pane_1);

        JLabel lblNewLabel = new JLabel("User Mode:");
        lblNewLabel.setBounds(10, 11, 79, 14);
        course_pane_1.add(lblNewLabel);
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 14));

        JLabel user = new JLabel(userMode);
        user.setBounds(10, 36, 78, 24);
        course_pane_1.add(user);
        user.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 14));
        
        JLabel lblNewLabel_1 = new JLabel("Welcome");
        lblNewLabel_1.setFont(new Font("Segoe Script", Font.BOLD | Font.ITALIC, 22));
        lblNewLabel_1.setBounds(0, 27, 113, 24);
        desktopPane_1.add(lblNewLabel_1);
        
        nameLabel = new JLabel("Back!!!");
        nameLabel.setFont(new Font("Segoe Script", Font.BOLD | Font.ITALIC, 22));
        nameLabel.setBounds(0, 46, 109, 40);
        desktopPane_1.add(nameLabel);
        
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-waving-hand-48.png"));
        lblNewLabel_2.setBounds(10, 81, 76, 49);
        desktopPane_1.add(lblNewLabel_2);

        updateCounts(); // Update counts when the dashboard is initialized
        
        // Initialize the activity text area
        activityTextArea = new JTextArea();
        activityTextArea.setEditable(false);
        activityTextArea.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        activityTextArea.setColumns(50); // Set the number of columns to make it wider


        // Add a scroll pane for the activity text area
        JScrollPane scrollPane = new JScrollPane(activityTextArea);
        scrollPane.setBounds(240, 170, 550, 279);
        contentPane.add(scrollPane);

        // Add a label for the activity history section
        JLabel activityLabel = new JLabel("Activity History");
        activityLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        activityLabel.setBounds(411, 141, 200, 20);
        contentPane.add(activityLabel);

        // Fetch and display activity history when the dashboard is initialized
        fetchAndDisplayActivityHistory();
    }

    private void fetchAndDisplayActivityHistory() {
        List<String> activityEntries = getActivityHistoryEntries();

        // Clear the text area before adding new entries
        activityTextArea.setText("");

        // Add each activity entry to the text area
        for (String entry : activityEntries) {
            activityTextArea.append(entry + "\n");
        }
    }

    // Method to fetch activity history entries from the database
    private List<String> getActivityHistoryEntries() {
        List<String> entries = new ArrayList<>();

        // Replace with your database connection details
        String url = "jdbc:mysql://localhost:3306/coursemanagement";
        String user = "root";
        String password = "12345";
        String query = "SELECT * FROM activity_history ORDER BY timestamp DESC LIMIT 10"; // Fetch recent 10 entries

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String userMode = resultSet.getString("user_mode");
                String timestamp = resultSet.getTimestamp("timestamp").toString();

                // Format the entry and add it to the list
                String entry = username + " created an account as " + userMode + " at " + timestamp;
                entries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch activity history from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return entries;
    }
    
    private void updateCounts() {
        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            int courseCount = fetchCount(connection, "courses");
            int teacherCount = fetchCount(connection, "teachers");
            int studentCount = fetchCount(connection, "student");

            no_of_courses.setText(String.valueOf(courseCount));
            no_of_teachers.setText(String.valueOf(teacherCount));
            no_of_students.setText(String.valueOf(studentCount));
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update counts from the database.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private int fetchCount(Connection connection, String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }
}
