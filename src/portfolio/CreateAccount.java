package portfolio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class CreateAccount {

	public static void insertData(String username, String emailOrPhone, String password, String phoneNumber,
			String course, Connection connection) throws SQLException {
		String sql = "INSERT INTO student (username, email, password, phone_number, course, created_at) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, emailOrPhone);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, phoneNumber);
			preparedStatement.setString(5, course);

			// Set the created_at field to the current timestamp
			Timestamp currentTimestamp = new Timestamp(new Date().getTime());
			preparedStatement.setTimestamp(6, currentTimestamp);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Data inserted successfully!");
			} else {
				System.out.println("Failed to insert data.");
			}
		}
	}
}
