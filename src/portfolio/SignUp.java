package portfolio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SignUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameInput;
	private JTextField emailInput;
	private JTextField phoneInput;
	private JPasswordField passwordInput;
	private JComboBox<String> userModeComboBox;
	private JComboBox<String> courseComboBox;

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

	public SignUp() {
		setResizable(false);
		setTitle("Course Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		ImageIcon userIcon = new ImageIcon("assests\\username.jpeg");
		ImageIcon scaledUserIcon = new ImageIcon(userIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JLabel userLogo = new JLabel(scaledUserIcon);
		userLogo.setBounds(45, 110, 30, 30);
		contentPane.add(userLogo);

		JLabel usernameLabel = new JLabel("Enter Username:");
		usernameLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		usernameLabel.setBounds(83, 100, 130, 20);
		contentPane.add(usernameLabel);

		JLabel header = new JLabel("Welcome to SignUp Panel");
		header.setFont(new Font("Segoe Print", Font.BOLD, 21));
		header.setBounds(60, 4, 305, 35);
		contentPane.add(header);

		JLabel emailLabel = new JLabel("Enter email or phone number:");
		emailLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		emailLabel.setBounds(83, 165, 210, 20);
		contentPane.add(emailLabel);

		usernameInput = new JTextField();
		usernameInput.setBounds(85, 120, 170, 20);
		contentPane.add(usernameInput);
		usernameInput.setColumns(10);

		emailInput = new JTextField();
		emailInput.setColumns(10);
		emailInput.setBounds(85, 185, 170, 20);
		contentPane.add(emailInput);

		ImageIcon anotherIcon = new ImageIcon("assests\\email.png");
		ImageIcon scaledAnotherIcon = new ImageIcon(
				anotherIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JLabel emailLogo = new JLabel(scaledAnotherIcon);
		emailLogo.setBounds(45, 167, 30, 30);
		contentPane.add(emailLogo);

		JLabel passwordLabel = new JLabel("Enter password:");
		passwordLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		passwordLabel.setBounds(83, 230, 210, 20);
		contentPane.add(passwordLabel);

		ImageIcon keyIcon = new ImageIcon("assests\\key.jpeg");
		ImageIcon scaledKeyIcon = new ImageIcon(keyIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JLabel keyLogo = new JLabel(scaledKeyIcon);
		keyLogo.setBounds(45, 242, 30, 30);
		contentPane.add(keyLogo);

		JLabel phoneLabel = new JLabel("Enter phone number:");
		phoneLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		phoneLabel.setBounds(83, 296, 210, 20);
		contentPane.add(phoneLabel);

		phoneInput = new JTextField();
		phoneInput.setColumns(10);
		phoneInput.setBounds(83, 320, 170, 20);
		contentPane.add(phoneInput);

		ImageIcon phoneIcon = new ImageIcon("assests\\phone.png");
		ImageIcon scaledPhoneIcon = new ImageIcon(phoneIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JLabel phoneLogo = new JLabel(scaledPhoneIcon);
		phoneLogo.setBounds(45, 310, 30, 30);
		contentPane.add(phoneLogo);

		userModeComboBox = new JComboBox<>(new String[] { "Select User Mode", "Student", "Instructor", "Admin" });
		userModeComboBox.setBounds(83, 370, 170, 30);
		contentPane.add(userModeComboBox);

		courseComboBox = new JComboBox<>();
		courseComboBox.setBounds(83, 430, 170, 30);
		contentPane.add(courseComboBox);

		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursemanagement",
							"root", "");

					String selectedCourse = (String) courseComboBox.getSelectedItem();

					CreateAccount.insertData(usernameInput.getText(), emailInput.getText(), passwordInput.getText(),
							phoneInput.getText(), selectedCourse, connection);

					connection.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setBackground(new Color(60, 179, 113));
		btnCreate.setFont(new Font("Arial", Font.BOLD, 14));
		btnCreate.setBounds(61, 500, 89, 30);
		contentPane.add(btnCreate);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
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
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(new Color(60, 179, 113));
		btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
		btnLogin.setBounds(204, 500, 89, 30);
		contentPane.add(btnLogin);

		ImageIcon cmsIcon = new ImageIcon("assests\\logo.png");
		ImageIcon scaledCmsIcon = new ImageIcon(cmsIcon.getImage().getScaledInstance(199, 65, Image.SCALE_SMOOTH));

		JLabel cmsLogo = new JLabel(scaledCmsIcon);
		cmsLogo.setBounds(83, 37, 199, 65);
		contentPane.add(cmsLogo);

		passwordInput = new JPasswordField();
		passwordInput.setBounds(83, 252, 172, 20);
		contentPane.add(passwordInput);

		userModeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedMode = (String) userModeComboBox.getSelectedItem();
				updateCourseDropdown(selectedMode);
			}
		});
	}

	private void updateCourseDropdown(String userMode) {
		if ("Student".equals(userMode)) {
			courseComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Select Course", "BCS", "BIBM", "IBM" }));
		} else {
			courseComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Select Course" }));
		}
	}
}