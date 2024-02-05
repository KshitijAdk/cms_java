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
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameInput;
	private JTextField emailInput;
	private JTextField passwordInput;

	private static final String DB_URL = "jdbc:mysql://localhost:3306/coursemanagement";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

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
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 367);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon userIcon = new ImageIcon("assests\\username.jpeg");
		ImageIcon scaledUserIcon = new ImageIcon(userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JLabel userLabel = new JLabel(scaledUserIcon);
		userLabel.setBounds(30, 140, 30, 30);
		contentPane.add(userLabel);

		JLabel usernameLabel = new JLabel("Enter Username:");
		usernameLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		usernameLabel.setBounds(70, 140, 130, 30);
		contentPane.add(usernameLabel);

		usernameInput = new JTextField();
		usernameInput.setBounds(200, 140, 170, 30);
		contentPane.add(usernameInput);
		usernameInput.setColumns(10);

		ImageIcon emailIcon = new ImageIcon("assests\\email.png");
		ImageIcon scaledEmailIcon = new ImageIcon(emailIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JLabel emailLabel = new JLabel(scaledEmailIcon);
		emailLabel.setBounds(30, 180, 30, 30);
		contentPane.add(emailLabel);

		JLabel emailLabelDesc = new JLabel("Enter Email:");
		emailLabelDesc.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		emailLabelDesc.setBounds(70, 180, 130, 30);
		contentPane.add(emailLabelDesc);

		emailInput = new JTextField();
		emailInput.setColumns(10);
		emailInput.setBounds(200, 180, 170, 30);
		contentPane.add(emailInput);

		ImageIcon passwordIcon = new ImageIcon("assests\\key.jpeg");
		ImageIcon scaledPasswordIcon = new ImageIcon(
				passwordIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JLabel passwordLabel = new JLabel(scaledPasswordIcon);
		passwordLabel.setBounds(30, 220, 30, 30);
		contentPane.add(passwordLabel);

		JLabel passwordLabelDesc = new JLabel("Enter Password:");
		passwordLabelDesc.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		passwordLabelDesc.setBounds(70, 220, 130, 30);
		contentPane.add(passwordLabelDesc);

		passwordInput = new JTextField();
		passwordInput.setColumns(10);
		passwordInput.setBounds(200, 220, 170, 30);
		contentPane.add(passwordInput);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String enteredUsername = usernameInput.getText();
		        String enteredEmail = emailInput.getText();
		        String enteredPassword = passwordInput.getText();

		        // Check credentials against the database
		        if (checkCredentials(enteredUsername, enteredEmail, enteredPassword)) {
		            // Credentials matched
		            showPopupMessage("Login successful!");

		            // Close the current login window
		            dispose();

		            // Open the main window of the course management system dashboard
		            EventQueue.invokeLater(() -> {
		                Dashboard mainDashboard = new Dashboard();
		                mainDashboard.setVisible(true);
		                mainDashboard.setLocationRelativeTo(null);
		            });
		        } else {
		            // Credentials did not match
		            showPopupMessage("Credentials did not match.");
		        }
		    }
		});

		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(60, 179, 113)); 
		btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
		btnLogin.setBounds(120, 280, 150, 40);
		contentPane.add(btnLogin);

		JLabel welcomeLabel = new JLabel("Welcome to Login Panel");
		welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 21));
		welcomeLabel.setBounds(80, 30, 300, 30);
		contentPane.add(welcomeLabel);
	}
	private void showPopupMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	private boolean checkCredentials(String username, String email, String password) {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			String sql = "SELECT * FROM student WHERE username = ? OR email = ? AND password = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, email);
				preparedStatement.setString(3, password);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					// If the query returns any rows, credentials are valid
					return resultSet.next();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}


}
