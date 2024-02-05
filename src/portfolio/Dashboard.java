package portfolio;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class Dashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
		setTitle("Dashboard");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 499);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(new Color(192, 192, 192));
		desktopPane.setBounds(0, 0, 204, 460);
		contentPane.add(desktopPane);
		
		JLabel cms_title = new JLabel("Course Management System");
		cms_title.setForeground(new Color(255, 255, 255));
		cms_title.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		cms_title.setBounds(10, 100, 184, 14);
		desktopPane.add(cms_title);
		
		JButton courses_btn = new JButton("Courses");
		courses_btn.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\course.png"));
		courses_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		courses_btn.setBounds(26, 184, 140, 23);
		desktopPane.add(courses_btn);
		
		JButton tutors_btn = new JButton("Tutors");
		tutors_btn.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\teacher.png"));
		tutors_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		tutors_btn.setBounds(26, 231, 140, 23);
		desktopPane.add(tutors_btn);
		
		JButton students_btn = new JButton("Students");
		students_btn.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\student.png"));
		students_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		students_btn.setBounds(26, 277, 140, 23);
		desktopPane.add(students_btn);
		
		JButton mail_btn = new JButton("Mail");
		mail_btn.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\mail.png"));
		mail_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		mail_btn.setBounds(26, 326, 140, 23);
		desktopPane.add(mail_btn);
		
		JButton settings_btn = new JButton("Settings");
		settings_btn.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\settings.png"));
		settings_btn.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		settings_btn.setBounds(26, 379, 140, 23);
		desktopPane.add(settings_btn);
		
		JLabel herald_logo = new JLabel("");
		herald_logo.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\herald.jpg"));
		herald_logo.setBounds(26, 11, 140, 78);
		desktopPane.add(herald_logo);
		
		JButton courses_btn_1 = new JButton("Dashboard");
		courses_btn_1.setIcon(new ImageIcon("C:\\Users\\Meridian\\Desktop\\eclipse-workspace\\CourseManagementSystem\\assests\\dashboard.png"));
		courses_btn_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		courses_btn_1.setBounds(26, 139, 140, 23);
		desktopPane.add(courses_btn_1);
		
		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBackground(new Color(192, 192, 192));
		desktopPane_1.setBounds(206, 0, 380, 116);
		contentPane.add(desktopPane_1);
		
		JLabel title = new JLabel("Dashboard");
		title.setBounds(144, 0, 97, 24);
		desktopPane_1.add(title);
		title.setBackground(new Color(255, 255, 255));
		title.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		
		JDesktopPane course_pane = new JDesktopPane();
		course_pane.setBorder(UIManager.getBorder("CheckBox.border"));
		course_pane.setBounds(25, 28, 94, 77);
		desktopPane_1.add(course_pane);
		course_pane.setBackground(new Color(206, 206, 206));
		
		JLabel total_course = new JLabel("   Total Courses");
		total_course.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
		total_course.setBounds(0, 0, 74, 25);
		course_pane.add(total_course);
		
		JLabel no_of_courses = new JLabel("0");
		no_of_courses.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		no_of_courses.setBounds(20, 27, 64, 39);
		course_pane.add(no_of_courses);
		
		JDesktopPane teachers_pane = new JDesktopPane();
		teachers_pane.setBorder(UIManager.getBorder("RadioButton.border"));
		teachers_pane.setBackground(new Color(206, 206, 206));
		teachers_pane.setBounds(147, 28, 94, 77);
		desktopPane_1.add(teachers_pane);
		
		JLabel total_teacher = new JLabel("   Total Teachers");
		total_teacher.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
		total_teacher.setBounds(0, 0, 84, 25);
		teachers_pane.add(total_teacher);
		
		JLabel no_of_teachers = new JLabel("0");
		no_of_teachers.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		no_of_teachers.setBounds(20, 27, 64, 39);
		teachers_pane.add(no_of_teachers);
		
		JDesktopPane students_pane = new JDesktopPane();
		students_pane.setBorder(UIManager.getBorder("RadioButton.border"));
		students_pane.setBackground(new Color(206, 206, 206));
		students_pane.setBounds(265, 28, 94, 77);
		desktopPane_1.add(students_pane);
		
		JLabel total_students = new JLabel("   Total Students");
		total_students.setFont(new Font("Trebuchet MS", Font.BOLD, 10));
		total_students.setBounds(0, 0, 84, 25);
		students_pane.add(total_students);
		
		JLabel no_of_students = new JLabel("0");
		no_of_students.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		no_of_students.setBounds(20, 27, 64, 39);
		students_pane.add(no_of_students);
	}
}
