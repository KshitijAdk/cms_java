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

public class Courses extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField searchField;
    private final String url = "jdbc:mysql://localhost:3306/coursemanagement";
    private final String username = "root";
    private final String password = "12345";

    private String userRole; // To store the role of the logged-in user
    private DefaultTableModel model;
    private JTable table; // Corrected data type

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Courses frame = new Courses("Admin");
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Courses(String userRole) {
        this.userRole = userRole;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 602, 480);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(128, 128, 128));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Courses");
        lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
        lblNewLabel.setBounds(27, 11, 99, 34);
        contentPane.add(lblNewLabel);

        searchField = new JTextField();
        searchField.setBounds(10, 87, 135, 23);
        contentPane.add(searchField);
        searchField.setColumns(10);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(searchField.getText());
            }
        });

        JButton btnNewButton = new JButton("Add Course");
        btnNewButton.setBackground(UIManager.getColor("Button.light"));
        btnNewButton.addActionListener(e -> {
            if ("Admin".equals(userRole)) {
                addCourseActionPerformed(e);
            } else {
                JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnNewButton.setBounds(171, 76, 119, 34);
        contentPane.add(btnNewButton);

        // Adjusted button positions
        JButton btnNewButton_1 = new JButton("Edit Course");
        btnNewButton_1.addActionListener(e -> {
            if ("Admin".equals(userRole)) {
                editCourseActionPerformed(e);
            } else {
                JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnNewButton_1.setBounds(320, 76, 110, 34);
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Delete Course");
        btnNewButton_2.addActionListener(e -> {
            if ("Admin".equals(userRole)) {
                deleteCourseActionPerformed(e);
            } else {
                JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnNewButton_2.setBounds(456, 76, 120, 34);
        contentPane.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("Back to Dashboard");
        btnNewButton_3.addActionListener(e -> dispose());
        btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-back-arrow-26.png"));
        btnNewButton_3.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        btnNewButton_3.setBounds(389, 0, 187, 34);
        contentPane.add(btnNewButton_3);

        JLabel lblNewLabel_1 = new JLabel("Search Here:");
        lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        lblNewLabel_1.setBounds(25, 63, 119, 14);
        contentPane.add(lblNewLabel_1);

        fetchAndPopulateCourses();
    }

    private void fetchAndPopulateCourses() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM courses";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Set all cells to non-editable
                    return false;
                }
            };

            model.addColumn("ID");
            model.addColumn("Course Name");
            model.addColumn("Seats");
            model.addColumn("Batch");
            model.addColumn("No of Years");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String courseName = resultSet.getString("course_name");
                String seats = resultSet.getString("seats");
                String batch = resultSet.getString("batch");
                String years = resultSet.getString("no_of_years");
                model.addRow(new String[]{id, courseName, seats, batch, years});
            }

            table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(10, 121, 566, 309);
            contentPane.add(scrollPane);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Error fetching courses from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourseActionPerformed(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField idField = new JTextField();
        JTextField courseNameField = new JTextField();
        JTextField seatsField = new JTextField();
        JTextField batchField = new JTextField();
        JTextField noOfYearsField = new JTextField();

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Course Name:"));
        panel.add(courseNameField);
        panel.add(new JLabel("Seats:"));
        panel.add(seatsField);
        panel.add(new JLabel("Batch:"));
        panel.add(batchField);
        panel.add(new JLabel("No of Years:"));
        panel.add(noOfYearsField);

        int result = JOptionPane.showConfirmDialog(contentPane, panel, "Enter Course Details", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String courseName = courseNameField.getText();
            String seats = seatsField.getText();
            String batch = batchField.getText();
            String noOfYears = noOfYearsField.getText();

            if (id.isEmpty() || courseName.isEmpty() || seats.isEmpty() || batch.isEmpty() || noOfYears.isEmpty()) {
                JOptionPane.showMessageDialog(contentPane, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int seatsInt = Integer.parseInt(seats);
                int noOfYearsInt = Integer.parseInt(noOfYears);
                model.addRow(new Object[]{id, courseName, seats, batch, noOfYears});
                addCourseToDatabase(id, courseName, seats, batch, noOfYears);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(contentPane, "Seats and No of Years must be numeric", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editCourseActionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] rowData = new Object[model.getColumnCount()];

            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = model.getValueAt(selectedRow, i);
            }

            JPanel panel = new JPanel(new GridLayout(5, 2));
            JTextField idField = new JTextField((String) rowData[0]);
            JTextField courseNameField = new JTextField((String) rowData[1]);
            JTextField seatsField = new JTextField((String) rowData[2]);
            JTextField batchField = new JTextField((String) rowData[3]);
            JTextField noOfYearsField = new JTextField((String) rowData[4]);

            panel.add(new JLabel("ID:"));
            panel.add(idField);
            panel.add(new JLabel("Course Name:"));
            panel.add(courseNameField);
            panel.add(new JLabel("Seats:"));
            panel.add(seatsField);
            panel.add(new JLabel("Batch:"));
            panel.add(batchField);
            panel.add(new JLabel("No of Years:"));
            panel.add(noOfYearsField);

            int result = JOptionPane.showConfirmDialog(contentPane, panel, "Edit Course", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                rowData[0] = idField.getText();
                rowData[1] = courseNameField.getText();
                rowData[2] = seatsField.getText();
                rowData[3] = batchField.getText();
                rowData[4] = noOfYearsField.getText();

                for (int i = 0; i < rowData.length; i++) {
                    model.setValueAt(rowData[i], selectedRow, i);
                }

                updateCourseInDatabase((String) rowData[0], (String) rowData[1], (String) rowData[2], (String) rowData[3], (String) rowData[4]);
            }
        } else {
            JOptionPane.showMessageDialog(contentPane, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCourseActionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (selectedRow < model.getRowCount()) {
                String idToDelete = (String) model.getValueAt(selectedRow, 0);
                model.removeRow(selectedRow);
                deleteCourseFromDatabase(idToDelete);
            } else {
                JOptionPane.showMessageDialog(contentPane, "Selected row index is out of bounds.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(contentPane, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourseToDatabase(String id, String courseName, String seats, String batch, String noOfYears) {
        String query = "INSERT INTO courses (id, course_name, seats, batch, no_of_years) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, courseName);
            statement.setString(3, seats);
            statement.setString(4, batch);
            statement.setString(5, noOfYears);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to add course to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCourseInDatabase(String id, String courseName, String seats, String batch, String noOfYears) {
        String query = "UPDATE courses SET course_name=?, seats=?, batch=?, no_of_years=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, courseName);
            statement.setString(2, seats);
            statement.setString(3, batch);
            statement.setString(4, noOfYears);
            statement.setString(5, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to update course in the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCourseFromDatabase(String id) {
        String query = "DELETE FROM courses WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to delete course from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }
}
