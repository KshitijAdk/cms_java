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
import java.time.LocalDateTime;

public class Teachers extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField searchField;
    private final String url = "jdbc:mysql://localhost:3306/coursemanagement";
    private final String user = "root";
    private final String password = "12345";
    private DefaultTableModel model;
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Teachers frame = new Teachers("Admin");
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressWarnings("serial")
    public Teachers(String userRole) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 602, 480);
        this.contentPane = new JPanel();
        this.contentPane.setBackground(new Color(128, 128, 128));
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);
        this.contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Teachers");
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

        JButton btnNewButton = new JButton("Add Teacher");
        btnNewButton.setBackground(UIManager.getColor("Button.light"));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equals(userRole)) {
                    addTeacherActionPerformed(e);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnNewButton.setBounds(171, 76, 119, 23);
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Edit Teacher");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equals(userRole)) {
                    editTeacherActionPerformed(e);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton_1.setBounds(320, 76, 110, 23);
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Delete Teacher");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("Admin".equals(userRole)) {
                    deleteTeacherActionPerformed(e);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "404 Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton_2.setBounds(456, 76, 120, 23);
        contentPane.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("Back to Dashboard");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnNewButton_3.setIcon(new ImageIcon(
                "C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-back-arrow-26.png"));
        btnNewButton_3.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
        btnNewButton_3.setBounds(389, 0, 187, 34);
        contentPane.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("Assign Teacher");
        btnNewButton_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAssignTeacher();
            }

            private void openAssignTeacher() {
                AssignTeacher assign = new AssignTeacher(userRole);
                assign.setVisible(true);
                assign.setLocationRelativeTo(null);
            }
        });
        btnNewButton_4.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
        btnNewButton_4.setBounds(136, 0, 135, 23);
        contentPane.add(btnNewButton_4);

        fetchAndPopulateTeachers();
    }

    private void fetchAndPopulateTeachers() {
        String query = "SELECT * FROM teachers";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addColumn("ID");
            model.addColumn("Username");
            model.addColumn("Email");
            model.addColumn("Phone Number");
            model.addColumn("Password");
            model.addColumn("Created At");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone_number");
                String password = "********"; // Mask the password
                String createdAt = resultSet.getString("created_at");

                model.addRow(new Object[]{id, username, email, phoneNumber, password, createdAt});
            }

            table = new JTable(model);

            table.setBackground(UIManager.getColor("Button.background"));
            table.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
            table.getTableHeader().setReorderingAllowed(false);

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(10, 121, 566, 309);
            contentPane.add(scrollPane);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Error fetching teachers from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTeacherActionPerformed(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JTextField idField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(contentPane, panel, "Enter Teacher Details",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String password = new String(passwordField.getPassword());
            // Error handling for ID validation
            if (!isInteger(id)) {
                JOptionPane.showMessageDialog(contentPane, "Please enter an integer value for ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (isIdExists(id)) {
                JOptionPane.showMessageDialog(contentPane, "ID already exists. Please enter a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String createdAt = LocalDateTime.now().toString();

            model.addRow(new Object[]{id, username, email, phoneNumber, password, createdAt});
            addTeacherToDatabase(id, username, email, phoneNumber, password, createdAt);
        }
    }

    private boolean isIdExists(String id) {
        String query = "SELECT COUNT(*) FROM teachers WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void editTeacherActionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] rowData = new Object[model.getColumnCount()];

            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = model.getValueAt(selectedRow, i);
            }

            JPanel panel = new JPanel(new GridLayout(5, 2));
            JTextField idField = new JTextField((String) rowData[0]);
            JTextField usernameField = new JTextField((String) rowData[1]);
            JTextField emailField = new JTextField((String) rowData[2]);
            JTextField phoneNumberField = new JTextField((String) rowData[3]);
            JPasswordField passwordField = new JPasswordField((String) rowData[4]);

            panel.add(new JLabel("ID:"));
            panel.add(idField);
            panel.add(new JLabel("Username:"));
            panel.add(usernameField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Phone Number:"));
            panel.add(phoneNumberField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordField);

            int result = JOptionPane.showConfirmDialog(contentPane, panel, "Edit Teacher",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                String newId = idField.getText();
                String newUsername = usernameField.getText();
                String newEmail = emailField.getText();
                String newPhoneNumber = phoneNumberField.getText();
                String newPassword = new String(passwordField.getPassword());

                model.setValueAt(newId, selectedRow, 0);
                model.setValueAt(newUsername, selectedRow, 1);
                model.setValueAt(newEmail, selectedRow, 2);
                model.setValueAt(newPhoneNumber, selectedRow, 3);
                model.setValueAt(newPassword, selectedRow, 4);

                updateTeacherInDatabase(newId, newUsername, newEmail, newPhoneNumber, newPassword);
            }
        } else {
            JOptionPane.showMessageDialog(contentPane, "Please select a row to edit.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTeacherActionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            if (selectedRow < model.getRowCount()) {
                String idToDelete = (String) model.getValueAt(selectedRow, 0);
                int option = JOptionPane.showConfirmDialog(contentPane,
                        "Are you sure you want to delete this teacher?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                    deleteTeacherFromDatabase(idToDelete);
                }
            } else {
                JOptionPane.showMessageDialog(contentPane, "Selected row index is out of bounds.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(contentPane, "Please select a row to delete.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTeacherToDatabase(String id, String username, String email, String phoneNumber, String password,
                                      String createdAt) {
        String query = "INSERT INTO teachers (id, username, email, phone_number, password, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, username);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            statement.setString(5, password);
            statement.setString(6, createdAt);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to add teacher to the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTeacherInDatabase(String id, String username, String email, String phoneNumber,
                                         String password) {
        String query = "UPDATE teachers SET username=?, email=?, phone_number=?, password=? WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, phoneNumber);
            statement.setString(4, password);
            statement.setString(5, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to update teacher in the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTeacherFromDatabase(String id) {
        String query = "DELETE FROM teachers WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Failed to delete teacher from the database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search(String query) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }
}
