package portfolio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class BaseActivity {
    protected String username;
    protected String userMode;
    protected java.sql.Timestamp timestamp;

    public BaseActivity(String username, String userMode, java.sql.Timestamp timestamp) {
        this.username = username;
        this.userMode = userMode;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserMode() {
        return userMode;
    }

    public void setUserMode(String userMode) {
        this.userMode = userMode;
    }

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(java.sql.Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

class ActivityDAO extends BaseActivity {

    private final String INSERT_ACTIVITY_SQL = "INSERT INTO activity_history (username, user_mode, timestamp) VALUES (?, ?, ?)";

    public ActivityDAO(String username, String userMode, java.sql.Timestamp timestamp) {
        super(username, userMode, timestamp);
    }

    public void insertActivity(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ACTIVITY_SQL)) {
            statement.setString(1, getUsername());
            statement.setString(2, getUserMode());
            statement.setTimestamp(3, getTimestamp());
            statement.executeUpdate();
        }
    }
}

class ActivityEntry extends BaseActivity {

    public ActivityEntry(String username, String userMode, java.sql.Timestamp timestamp) {
        super(username, userMode, timestamp);
    }

    // Additional methods or overrides specific to ActivityEntry
}
