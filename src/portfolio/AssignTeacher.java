package portfolio;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class AssignTeacher extends JFrame {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> teacherComboBox;
    private JComboBox<String> moduleComboBox;
    private DefaultTableModel tableModel;
    private JTable table;
    private static String loggedInUserRole;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/coursemanagement";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AssignTeacher frame = new AssignTeacher("Admin");
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AssignTeacher(String userRole) {
        loggedInUserRole = userRole;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JScrollPane teacherScrollPane = new JScrollPane();
        teacherScrollPane.setBounds(50, 75, 239, 30);
        contentPane.add(teacherScrollPane);

        teacherComboBox = new JComboBox<>();
        teacherScrollPane.setViewportView(teacherComboBox);

        JLabel lblNewLabel = new JLabel("Assign Teachers");
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 21));
        lblNewLabel.setBounds(26, 11, 237, 38);
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Assign");
        btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        btnNewButton.setBounds(194, 116, 122, 30);
        contentPane.add(btnNewButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Teacher");
        tableModel.addColumn("Module");

        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 150, 700, 300);
        contentPane.add(tableScrollPane);

        moduleComboBox = new JComboBox<>();
        moduleComboBox.setBounds(385, 75, 270, 28);
        contentPane.add(moduleComboBox);

        JButton btnNewButton_1 = new JButton("Back to Teachers Page");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnNewButton_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        btnNewButton_1.setBounds(297, 520, 180, 35);
        contentPane.add(btnNewButton_1);
        
        JButton btnDeleteAssign = new JButton("Delete Assign");
        btnDeleteAssign.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteAssignButtonClicked(); // Call method to handle delete action
            }
        });
        btnDeleteAssign.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        btnDeleteAssign.setBounds(395, 116, 180, 30);
        contentPane.add(btnDeleteAssign);

        // Populate combo boxes and table from database
        populateTeacherComboBox();
        populateSubjectComboBox();
        populateTableFromDatabase();

        // Assign button action listener
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                assignButtonClicked(); // Call method to handle assign action
            }
        });
    }

    // Method to handle assign button click
    private void assignButtonClicked() {
        String teacher = teacherComboBox.getSelectedItem().toString();
        String module = moduleComboBox.getSelectedItem().toString();

        if (teacher.equals("Select Teacher") || module.equals("Select Module")) {
            JOptionPane.showMessageDialog(this, "Please select a valid Teacher and Module.",
                    "Invalid Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{teacher, module});
        saveToDatabase(teacher, module);
    }

    // Method to handle delete assign button click
    private void deleteAssignButtonClicked() {
        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.",
                    "No Row Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String teacher = tableModel.getValueAt(selectedRowIndex, 0).toString();
        String module = tableModel.getValueAt(selectedRowIndex, 1).toString();

        deleteFromDatabase(teacher, module);
        tableModel.removeRow(selectedRowIndex);
    }

    // Method to delete data from the database
    private void deleteFromDatabase(String teacher, String module) {
        // Connect to the database and delete data from assign_teacher table
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM assign_teacher WHERE teacher = ? AND module = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, teacher);
                statement.setString(2, module);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to delete data from the database.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


	// Method to populate teacher combo box from database
    private void populateTeacherComboBox() {
        teacherComboBox.addItem("Select Teacher");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT username FROM teachers";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    teacherComboBox.addItem(username);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to retrieve teacher usernames from the database.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to populate subject combo box
    private void populateSubjectComboBox() {
        moduleComboBox.addItem("Select Module");

        String[] modules = { "4CI018 Academic Skills and Team-based Learning",
                "4CS001 Introductory Programming and Problem Solving", "4CS015 Fundamentals of Computing",
                "4NCC01 Web Technologies", "4CS016 Embedded Systems Programming",
                "4CS017 Internet Software Architecture", "4MM013 Computational Mathematics",
                "5CS019 Object-Oriented Design and Programming", "5CS021 Numerical Methods and Concurrency",
                "5CS037 Concept and Technologies of AI" };

        for (String module : modules) {
            moduleComboBox.addItem(module);
        }
    }

    // Method to save data to database
    private void saveToDatabase(String teacher, String module) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO assign_teacher (teacher, module) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, teacher);
                statement.setString(2, module);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save data to the database.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to populate table from database
    private void populateTableFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT teacher, module FROM assign_teacher";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                tableModel.setRowCount(0);

                while (resultSet.next()) {
                    String teacher = resultSet.getString("teacher");
                    String module = resultSet.getString("module");
                    tableModel.addRow(new Object[]{teacher, module});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to retrieve data from database.", "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
