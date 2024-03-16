package portfolio;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Settings extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static Dashboard dashboardFrame; // Reference to the Dashboard frame

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Settings frame = new Settings("Admin", dashboardFrame);
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
    public Settings(String userRole, Dashboard dashboardFrame) {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 496, 319);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblNewLabel = new JLabel("Settings");
        lblNewLabel.setBackground(new Color(128, 128, 128));
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNewLabel, BorderLayout.NORTH);

        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(new Color(128, 128, 128));
        contentPane.add(panelButtons, BorderLayout.CENTER);
        panelButtons.setLayout(new GridLayout(2, 1, 0, 10));

        JPanel panelCreate = new JPanel();
        panelCreate.setBackground(new Color(192, 192, 192));
        panelButtons.add(panelCreate);
        panelCreate.setLayout(null);

        JButton btnCreateResultSlip = new JButton("Create Result Slip");
        btnCreateResultSlip.setBounds(165, 66, 159, 25);
        btnCreateResultSlip.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        panelCreate.add(btnCreateResultSlip);

        JButton btnPrintResultSlip = new JButton("Print Result Slip");
        btnPrintResultSlip.setBounds(165, 11, 159, 25);
        panelCreate.add(btnPrintResultSlip);
        btnPrintResultSlip.setFont(new Font("Trebuchet MS", Font.BOLD, 13));

        // Action listener for Print Result Slip button
        btnPrintResultSlip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equals(userRole)) {
                    printResultSlip();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panelPrint = new JPanel();
        panelPrint.setBackground(new Color(192, 192, 192));
        panelButtons.add(panelPrint);
        panelPrint.setLayout(null);

        JButton btnLogOut = new JButton("Log Out");
        btnLogOut.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-log-out-32.png"));
        btnLogOut.setBounds(0, 81, 134, 34);
        panelPrint.add(btnLogOut);
        btnLogOut.setFont(new Font("Trebuchet MS", Font.BOLD, 16));

        JButton btnNewButton = new JButton("Back to Dashboard");
        btnNewButton.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-back-arrow-26.png"));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        btnNewButton.setBounds(125, 0, 204, 34);
        panelPrint.add(btnNewButton);

        // Action listener for Log Out button
        btnLogOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to log out?",
                        "Confirm Logout", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(contentPane, "Logged out successfully!");
                    dispose();
                    openLoginPanel();
                }
            }

            private void openLoginPanel() {
                Login loginPanel = new Login();
                loginPanel.setVisible(true);
                loginPanel.setLocationRelativeTo(null);
                dashboardFrame.dispose();
            }
        });

        // Action listener for Create Result Slip button
        btnCreateResultSlip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equals(userRole)) {
                    createResultSlip();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Method to create the result slip
    private void createResultSlip() {
        JTextField studentNameField = new JTextField();
        JTextField module1Field = new JTextField();
        JTextField grade1Field = new JTextField();
        JTextField module2Field = new JTextField();
        JTextField grade2Field = new JTextField();
        JTextField module3Field = new JTextField();
        JTextField grade3Field = new JTextField();
        JTextField reviewField = new JTextField();

        Object[] fields = { "Student Name:", studentNameField, "Name of Module 1:", module1Field, "Module 1 Grade:",
                grade1Field, "Name of Module 2:", module2Field, "Module 2 Grade:", grade2Field, "Name of Module 3:",
                module3Field, "Module 3 Grade:", grade3Field, "Review:", reviewField };

        int result = JOptionPane.showConfirmDialog(Settings.this, fields, "Create Result Slip",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String studentName = studentNameField.getText();
            String module1 = module1Field.getText();
            String grade1 = grade1Field.getText();
            String module2 = module2Field.getText();
            String grade2 = grade2Field.getText();
            String module3 = module3Field.getText();
            String grade3 = grade3Field.getText();
            String review = reviewField.getText();

            // Validate input here if needed

            // Save the result slip to the database
            saveResultSlipToDatabase(studentName, module1, grade1, module2, grade2, module3, grade3, review);
        }
    }

    private void saveResultSlipToDatabase(String studentName, String module1, String grade1, String module2,
            String grade2, String module3, String grade3, String review) {
        String url = "jdbc:mysql://localhost:3306/coursemanagement";
        String user = "root";
        String password = "12345";
        String query = "INSERT INTO result (name, module1, grade1, module2, grade2, module3, grade3, review) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(query,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, studentName);
            statement.setString(2, module1);
            statement.setString(3, grade1);
            statement.setString(4, module2);
            statement.setString(5, grade2);
            statement.setString(6, module3);
            statement.setString(7, grade3);
            statement.setString(8, review);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating result slip failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    JOptionPane.showMessageDialog(Settings.this, "Result slip created successfully with ID: " + id);
                } else {
                    throw new SQLException("Creating result slip failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Settings.this, "Failed to create result slip.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to print the result slip
    private void printResultSlip() {
        JTextField idField = new JTextField();
        JTextField studentNameField = new JTextField();

        Object[] fields = { "ID:", idField, "Student Name:", studentNameField };

        int result = JOptionPane.showConfirmDialog(Settings.this, fields, "Print Result Slip",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String studentName = studentNameField.getText();

            // Fetch result information from the database
            fetchAndShowResultSlip(id, studentName);
        }
    }

    private void fetchAndShowResultSlip(String id, String studentName) {
        String url = "jdbc:mysql://localhost:3306/coursemanagement";
        String user = "root";
        String password = "12345";
        String query = "SELECT * FROM result WHERE id = ? AND name = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);
            statement.setString(2, studentName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Display the result in a new frame
                    JFrame frame = new JFrame();
                    frame.setBounds(100, 100, 450, 300);
                    JPanel panel = new JPanel();
                    frame.setContentPane(panel);
                    panel.setLayout(null);

                    JLabel lblNewLabel = new JLabel("Result of " + studentName);
                    lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
                    lblNewLabel.setBounds(10, 11, 300, 29);
                    panel.add(lblNewLabel);

                    JLabel lblModule1 = new JLabel("Module 1: " + resultSet.getString("module1") + " | Grade: "
                            + resultSet.getString("grade1"));
                    lblModule1.setBounds(10, 50, 300, 14);
                    panel.add(lblModule1);

                    JLabel lblModule2 = new JLabel("Module 2: " + resultSet.getString("module2") + " | Grade: "
                            + resultSet.getString("grade2"));
                    lblModule2.setBounds(10, 80, 300, 14);
                    panel.add(lblModule2);

                    JLabel lblModule3 = new JLabel("Module 3: " + resultSet.getString("module3") + " | Grade: "
                            + resultSet.getString("grade3"));
                    lblModule3.setBounds(10, 110, 300, 14);
                    panel.add(lblModule3);

                    JLabel lblReview = new JLabel("Review: " + resultSet.getString("review"));
                    lblReview.setBounds(10, 140, 300, 14);
                    panel.add(lblReview);

                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Settings.this,
                            "No result found for the provided ID and student name.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Settings.this, "Failed to fetch result slip.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
