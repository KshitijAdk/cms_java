package portfolio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;

public class Students extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField searchField;
    private DefaultTableModel model;
    private final String url = "jdbc:mysql://localhost:3306/coursemanagement";
    private final String user = "root";
    private final String password = "12345";
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Students frame = new Students("Admin");
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Students(String userRole) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600); // Adjusted window size for better visibility
        contentPane = new JPanel();
        contentPane.setBackground(new Color(128, 128, 128));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Students");
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
        lblNewLabel.setBounds(27, 11, 99, 34);
        contentPane.add(lblNewLabel);

        searchField = new JTextField();
        searchField.setBounds(26, 77, 135, 20);
        contentPane.add(searchField);
        searchField.setColumns(10);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                search(searchField.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                search(searchField.getText());
            }

            public void changedUpdate(DocumentEvent e) {
                search(searchField.getText());
            }
        });

        JButton btnAddStudent = new JButton("Add Student");
        btnAddStudent.setBackground(UIManager.getColor("Button.light"));
        btnAddStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equals(userRole)) {
                    addStudent();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnAddStudent.setBounds(171, 76, 119, 23);
        contentPane.add(btnAddStudent);

        JButton btnEditStudent = new JButton("Edit Student");
        btnEditStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equals(userRole)) {
                    editStudent();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEditStudent.setBounds(320, 76, 110, 23);
        contentPane.add(btnEditStudent);

        JButton btnDeleteStudent = new JButton("Delete Student");
        btnDeleteStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equals(userRole)) {
                    deleteStudent();
                } else {
                    JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnDeleteStudent.setBounds(456, 76, 120, 23);
        contentPane.add(btnDeleteStudent);

        JButton btnBackToDashboard = new JButton("Back to Dashboard");
        btnBackToDashboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBackToDashboard.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        btnBackToDashboard.setBounds(600, 0, 187, 34);
        contentPane.add(btnBackToDashboard);

        fetchAndPopulateStudents();
    }

    private void fetchAndPopulateStudents() {
        String query = "SELECT username, email, password, phone_number, course, created_at FROM student";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("Username");
            model.addColumn("Email");
            model.addColumn("Password");
            model.addColumn("Phone Number");
            model.addColumn("Course");
            model.addColumn("Created At");

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = "********"; // Mask the password
                String phoneNumber = resultSet.getString("phone_number");
                String course = resultSet.getString("course");
                String createdAt = resultSet.getString("created_at");

                model.addRow(new Object[]{username, email, password, phoneNumber, course, createdAt});
            }

            table = new JTable(model);

            table.setBackground(UIManager.getColor("Button.background"));
            table.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
            table.getTableHeader().setReorderingAllowed(false);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(10, 121, 766, 429);
            contentPane.add(scrollPane);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Error fetching students from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }

    private void addStudent() {
        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField phoneNumberField = new JTextField();
        JTextField courseField = new JTextField();

        Object[] fields = {"Username:", usernameField, "Email:", emailField, "Password:", passwordField,
                "Phone Number:", phoneNumberField, "Course:", courseField};

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String phoneNumber = phoneNumberField.getText();
            String course = courseField.getText();
            String createdAt = getCurrentTimestamp();

            model.addRow(new Object[]{username, email, password, phoneNumber, course, createdAt});
            addStudentToDatabase(username, email, password, phoneNumber, course, createdAt);
        }
    }

    private String getCurrentTimestamp() {
        return LocalDate.now().toString();
    }

    private void addStudentToDatabase(String username, String email, String password, String phoneNumber, String course,
                                       String createdAt) {
        String query = "INSERT INTO student (username, email, password, phone_number, course, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, phoneNumber);
            statement.setString(5, course);
            statement.setString(6, createdAt);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to add student to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) table.getValueAt(selectedRow, 0);
            String email = (String) table.getValueAt(selectedRow, 1);
            String password = (String) table.getValueAt(selectedRow, 2);
            String phoneNumber = (String) table.getValueAt(selectedRow, 3);
            String course = (String) table.getValueAt(selectedRow, 4);

            JPanel panel = new JPanel(new GridLayout(5, 2));
            JTextField usernameField = new JTextField(username);
            JTextField emailField = new JTextField(email);
            JTextField passwordField = new JTextField(password);
            JTextField phoneNumberField = new JTextField(phoneNumber);

            panel.add(new JLabel("Username:"));
            panel.add(usernameField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordField);
            panel.add(new JLabel("Phone Number:"));
            panel.add(phoneNumberField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Edit Student", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                updateStudentInDatabase(username, email, usernameField.getText(), emailField.getText(),
                        passwordField.getText(), phoneNumberField.getText());
                fetchAndPopulateStudents();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) table.getValueAt(selectedRow, 0);
            String email = (String) table.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                deleteStudentFromDatabase(username, email);
                fetchAndPopulateStudents();
                table.clearSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudentInDatabase(String oldUsername, String oldEmail, String newUsername, String newEmail,
                                         String password, String phoneNumber) {
        String query = "UPDATE student SET username=?, email=?, password=?, phone_number=? WHERE username=? AND email=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newUsername);
            statement.setString(2, newEmail);
            statement.setString(3, password);
            statement.setString(4, phoneNumber);
            statement.setString(5, oldUsername);
            statement.setString(6, oldEmail);

            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to update student in the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudentFromDatabase(String username, String email) {
        String query = "DELETE FROM student WHERE username=? AND email=?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to delete student from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
