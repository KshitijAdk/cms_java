package portfolio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class CreateAccount {

	public static void insertData(String username, String emailOrPhone, String password, String phoneNumber,
			String course, String userType, Connection connection) throws SQLException {
		String tableName;
		String sql;
		switch (userType) {
		case "Student":
			tableName = "student";
			sql = "INSERT INTO student (username, email, password, phone_number, created_at,course) VALUES (?, ?, ?, ?, ?, ?)";
			break;
		case "Instructor":
			tableName = "teachers";
			sql = "INSERT INTO teachers (username, email, password, phone_number, created_at) VALUES (?, ?, ?, ?, ?)";
			break;
		case "Admin":
			tableName = "admin";
			sql = "INSERT INTO admin (username, email, password, phone_number, created_at) VALUES (?, ?, ?, ?, ?)";
			break;
		default:
			throw new IllegalArgumentException("Invalid user type: " + userType);
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, emailOrPhone);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, phoneNumber);

			// Set the created_at field to the current timestamp
			Timestamp currentTimestamp = new Timestamp(new Date().getTime());
			preparedStatement.setTimestamp(5, currentTimestamp);

			// Set the course only if the user is a student
			if (userType.equals("Student")) {
				// Set the parameter index to 6 for the course column
				preparedStatement.setString(6, course);
			}

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Data inserted successfully!");
			} else {
				System.out.println("Failed to insert data.");
			}
		}
	}
}
