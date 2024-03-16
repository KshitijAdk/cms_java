package portfolio;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.DefaultComboBoxModel;

public class SignUp extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField;
    private JPasswordField passwordField;
    private JComboBox<String> courseCombo;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SignUp frame = new SignUp();
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
    public SignUp() {
    	setResizable(false);
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\logo.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 410, 570);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(218, 218, 218));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome to Sign Up Panel");
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
        lblNewLabel.setBounds(60, 11, 317, 44);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-course-96.png"));
        lblNewLabel_1.setBounds(128, 49, 120, 90);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Enter username");
        lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblNewLabel_2.setBounds(99, 169, 149, 26);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("Enter email or phone number");
        lblNewLabel_2_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblNewLabel_2_1.setBounds(99, 224, 202, 26);
        contentPane.add(lblNewLabel_2_1);
        JLabel lblNewLabel_2_1_1 = new JLabel("Enter password");
        lblNewLabel_2_1_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblNewLabel_2_1_1.setBounds(99, 279, 149, 26);
        contentPane.add(lblNewLabel_2_1_1);

        textField_2 = new JTextField();
        textField_2.setBackground(UIManager.getColor("FormattedTextField.inactiveBackground"));
        textField_2.setColumns(10);
        textField_2.setBounds(99, 248, 192, 20);
        MatteBorder textField2_Border = new MatteBorder(0, 0, 1, 0, Color.GRAY);
        textField_2.setBorder(textField2_Border);
        contentPane.add(textField_2);

        JLabel lblNewLabel_2_1_1_1 = new JLabel("Enter phone number");
        lblNewLabel_2_1_1_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblNewLabel_2_1_1_1.setBounds(99, 334, 149, 26);
        contentPane.add(lblNewLabel_2_1_1_1);

        textField_3 = new JTextField();
        textField_3.setBackground(UIManager.getColor("FormattedTextField.inactiveBackground"));
        textField_3.setColumns(10);
        textField_3.setBounds(99, 360, 192, 20);
        MatteBorder textField3_Border = new MatteBorder(0, 0, 1, 0, Color.GRAY);
        textField_3.setBorder(textField3_Border);
        contentPane.add(textField_3);

        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-username-30.png"));
        lblNewLabel_3.setBounds(60, 169, 30, 44);
        contentPane.add(lblNewLabel_3);

        textField = new JTextField();
        textField.setBackground(UIManager.getColor("FormattedTextField.inactiveBackground"));
        textField.setColumns(10);
        textField.setBounds(99, 193, 192, 20);
        MatteBorder textField_Border = new MatteBorder(0, 0, 1, 0, Color.GRAY);
        textField.setBorder(textField_Border);

        contentPane.add(textField);

        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-email-30.png"));
        lblNewLabel_4.setBounds(60, 224, 36, 44);
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-key-30.png"));
        lblNewLabel_5.setBounds(60, 286, 36, 37);
        contentPane.add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("");
        lblNewLabel_6.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-telephone-30.png"));
        lblNewLabel_6.setBounds(60, 341, 33, 37);
        contentPane.add(lblNewLabel_6);



        // Combo box for selecting user mode
        JComboBox<String> userCombo = new JComboBox<>();
        userCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected item from the combo box
                String selectedUserMode = (String) userCombo.getSelectedItem();
                // Check if the selected user mode is "Student"
                if (selectedUserMode.equals("Student")) {
                    // Show the second combo box for course selection
                    courseCombo.setVisible(true);
                } else {
                    // Hide the second combo box if user mode is not "Student"
                    courseCombo.setVisible(false);
                }
            }
        });
        userCombo.setModel(new DefaultComboBoxModel<>(new String[] { "Select User Mode", "Student", "Instructor", "Admin" }));
        userCombo.setBackground(UIManager.getColor("Button.disabledShadow"));
        userCombo.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        userCombo.setBounds(99, 403, 149, 22);
        contentPane.add(userCombo);
        
        courseCombo = new JComboBox<>();
        courseCombo.setModel(new DefaultComboBoxModel<>(new String[] { "Select a Course:", "BCS", "IBM", "BIBM"}));
        courseCombo.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        courseCombo.setBackground(Color.WHITE);
        courseCombo.setBounds(99, 451, 149, 22);
        // Initially hide the second combo box
        courseCombo.setVisible(false);
        contentPane.add(courseCombo);

        JLabel lblNewLabel_7 = new JLabel("");
        lblNewLabel_7.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-user-30.png"));
        lblNewLabel_7.setBounds(60, 389, 30, 37);
        contentPane.add(lblNewLabel_7);

        JButton btnNewButton = new JButton("Sign Up");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user inputs
                String username = textField.getText();
                String emailOrPhone = textField_2.getText();
                String password = new String(passwordField.getPassword());
                String phoneNumber = textField_3.getText();
                String userMode = (String) userCombo.getSelectedItem(); // Get selected user mode
                String course = "";

                // Check if the selected item is valid
                if (!userMode.equals("Select User Mode")) {
                    // If the user is a student, get the selected course
                    if (userMode.equals("Student")) {
                        course = (String) courseCombo.getSelectedItem(); // Get selected course
                    }

                    // Validate email pattern
                    if (isValidEmail(emailOrPhone)) {
                        // Replace the placeholder values with your actual database information
                        String databaseName = "coursemanagement";
                        String dbUsername = "root";
                        String dbPassword = "12345";

                        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName,
                                dbUsername, dbPassword)) {
                            // Pass the correct user type
                            signUp(username, emailOrPhone, password, phoneNumber, userMode, course, connection);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            showPopupMessage("Error: Could not connect to the database.");
                        }
                    } else {
                        showPopupMessage("Please enter a valid email address.");
                    }
                } else {
                    showPopupMessage("Please select a valid user mode.");
                }
            }
        });

        btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        btnNewButton.setBounds(60, 500, 89, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Sign In");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                openLoginPanel();
            }

            private void openLoginPanel() {
                Login loginPanel = new Login();
                loginPanel.setVisible(true);
                loginPanel.setLocationRelativeTo(null);
            }
        });
        btnNewButton_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        btnNewButton_1.setBounds(176, 500, 89, 23);
        contentPane.add(btnNewButton_1);

        passwordField = new JPasswordField();
        passwordField.setBounds(99, 303, 192, 20);
        passwordField.setBackground(UIManager.getColor("FormattedTextField.inactiveBackground"));
        contentPane.add(passwordField);
        MatteBorder pass_Field = new MatteBorder(0, 0, 1, 0, Color.GRAY);
        passwordField.setBorder(pass_Field);

    }

    // Inside the signUp method
    private void signUp(String username, String emailOrPhone, String password, String phoneNumber, String userMode,
                        String course, Connection connection) {
        String tableName;

        // Determine the table name based on the selected user mode
        switch (userMode) {
            case "Student":
                tableName = "Student";
                break;
            case "Instructor":
                tableName = "Instructor";
                course = null; // Set course to null since instructors do not have a course
                break;
            case "Admin":
                tableName = "Admin";
                course = null; // Set course to null since admins do not have a course
                break;
            default:
                System.out.println("Invalid user mode: " + userMode);
                return;
        }

        try {
            // Use a secure method to retrieve the password (e.g., getPasswordAsString)
            // Insert the data into the appropriate table with course name for students
            CreateAccount.insertData(username, emailOrPhone, password, phoneNumber, course, tableName, connection);

            // Insert activity entry
            Timestamp timestamp = new Timestamp(new Date().getTime());
            insertActivityEntry(username, userMode, timestamp, connection);

            showPopupMessage("User signed up successfully!");

            dispose();
            // Open the login window
            openLoginPanel();
            // Add any additional logic or UI updates after successful sign-up
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle sign-up failure, show an error message or take appropriate action
            showPopupMessage("Error: Could not sign up. Please try again later.");
        }
    }

    private void insertActivityEntry(String username, String userMode, Timestamp timestamp, Connection connection) {
        ActivityDAO activityDAO = new ActivityDAO(username, userMode, timestamp);
        try {
            activityDAO.insertActivity(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle activity entry insertion failure
            showPopupMessage("Error: Could not insert activity entry.");
        }
    }

    private void openLoginPanel() {
        Login loginPanel = new Login();
        loginPanel.setVisible(true);
        loginPanel.setLocationRelativeTo(null);
    }

    private void showPopupMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Validate email pattern
    private boolean isValidEmail(String email) {
        String emailPattern = "^.+@gmail\\.com$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
