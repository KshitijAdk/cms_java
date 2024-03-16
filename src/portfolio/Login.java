package portfolio;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailInput;
	private JTextField passwordInput;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/coursemanagement";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "12345";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\logo.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 505);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(221, 221, 221));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon userIcon = new ImageIcon("assests\\username.jpeg");
		ImageIcon scaledUserIcon = new ImageIcon(userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JLabel userLabel = new JLabel(scaledUserIcon);
		userLabel.setBounds(30, 140, 30, 30);
		contentPane.add(userLabel);

		JLabel emailLabelDesc = new JLabel("Enter Email:");
		emailLabelDesc.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		emailLabelDesc.setBounds(138, 216, 106, 30);
		contentPane.add(emailLabelDesc);

		emailInput = new JTextField();
		emailInput.setBackground(UIManager.getColor("FormattedTextField.inactiveBackground"));
		emailInput.setColumns(10);
		emailInput.setBounds(138, 240, 170, 30);
		MatteBorder emailInput_Border = new MatteBorder(0, 0, 1, 0, Color.GRAY);
		emailInput.setBorder(emailInput_Border);
		contentPane.add(emailInput);

		JLabel passwordLabelDesc = new JLabel("Enter Password:");
		passwordLabelDesc.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		passwordLabelDesc.setBounds(138, 283, 106, 30);
		contentPane.add(passwordLabelDesc);

		passwordInput = new JTextField();
		passwordInput.setBackground(UIManager.getColor("FormattedTextField.inactiveBackground"));
		passwordInput.setColumns(10);
		passwordInput.setBounds(138, 307, 170, 30);
		MatteBorder passwordInput_Border = new MatteBorder(0, 0, 1, 0, Color.GRAY);
		passwordInput.setBorder(passwordInput_Border);
		contentPane.add(passwordInput);

		JLabel welcomeLabel = new JLabel("Welcome to Login Panel");
		welcomeLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
		welcomeLabel.setBounds(70, 30, 300, 30);
		contentPane.add(welcomeLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(
				"C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-course-96.png"));
		lblNewLabel_1.setBounds(138, 58, 120, 90);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-email-30.png"));
		lblNewLabel.setBounds(97, 232, 42, 38);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-key-30.png"));
		lblNewLabel_2.setBounds(97, 299, 42, 38);
		contentPane.add(lblNewLabel_2);

		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Student", "Instructor", "Admin" }));
		comboBox.setBounds(138, 174, 139, 22);
		contentPane.add(comboBox);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\icons8-user-30.png"));
		lblNewLabel_3.setBounds(97, 159, 42, 53);
		contentPane.add(lblNewLabel_3);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = emailInput.getText();
				String password = passwordInput.getText();
				String userMode = (String) comboBox.getSelectedItem();

				if (checkCredentials(email, password, userMode)) {
					dispose();
					openDashboard(userMode);
					// Credentials match, perform appropriate actions (e.g., navigate to the main
					// application)
				} else {
					// Credentials do not match, show popup message
					showPopupMessage("Credentials did not match.");
				}
			}

			private void openDashboard(String userMode) {
				Dashboard dashboard = new Dashboard(userMode, null); // Pass user mode to Dashboard
				dashboard.setVisible(true);
				dashboard.setLocationRelativeTo(null);
			}
		});
		btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		btnNewButton.setBounds(161, 365, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				openSignUpPage();
			}

			private void openSignUpPage() {
				SignUp signUpPage = new SignUp();
				signUpPage.setVisible(true);
				signUpPage.setLocationRelativeTo(null);
			}
		});
		btnCreateAccount.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		btnCreateAccount.setBounds(138, 432, 139, 23);
		contentPane.add(btnCreateAccount);
		
		JLabel lblNewLabel_4 = new JLabel("Don't have an account?");
		lblNewLabel_4.setFont(new Font("Sitka Text", Font.ITALIC, 14));
		lblNewLabel_4.setBounds(138, 414, 170, 14);
		contentPane.add(lblNewLabel_4);
	}

	private void showPopupMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	private boolean checkCredentials(String email, String password, String userMode) {
		String tableName;

		// Determine the table to query based on the selected user mode
		if ("Student".equals(userMode)) {
			tableName = "student";
		} else if ("Instructor".equals(userMode)) {
			tableName = "teachers";
		} else if ("Admin".equals(userMode)) {
			tableName = "admin";
		} else {
			System.out.println("Invalid user mode: " + userMode);
			return false;
		}

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			// Construct the SQL query based on the selected user mode
			String sql = "SELECT * FROM " + tableName + " WHERE email = ? AND password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					// If the query returns any rows, credentials are valid
					if (resultSet.next()) {
						// Return true to indicate successful login
						return true;
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		// Return false to indicate unsuccessful login
		return false;
	}
}