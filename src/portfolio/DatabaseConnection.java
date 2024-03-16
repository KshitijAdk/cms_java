package portfolio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	/**
	 * Launch the application.
	 */
	static final String DB_URL = "jdbc:mysql://localhost/coursemanagement";
	static final String USERNAME = "root";
	static final String PASSWORD = "12345";

	public static void main(String[] args) {

		try {

			System.out.print("Connecting to database....");
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Statement stmt = connection.createStatement();
//                    System.out.print(stmt);

			System.out.println();

			if (connection != null) {
				System.out.println("Database connected successfully!");
			}

		} catch (SQLException exc) {
			System.out.println("Something went wrong while connecting to the database.");
			System.out.println(exc);
		}

	}

}