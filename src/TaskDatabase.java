import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDatabase {
    private Connection conn;
    public TaskDatabase(String dbName) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        createTable();
    }
    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, assignedTo TEXT, dayStart INTEGER, duration INTEGER)";
        conn.createStatement().execute(sql);
    }
    public void addTask(Task task) throws SQLException {
        String sql = "INSERT INTO Tasks(name, assignedTo, dayStart, duration) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getAssignedTo());
            stmt.setInt(3, task.getDayStart());
            stmt.setInt(4, task.getDuration());
            stmt.executeUpdate();
        }
    }

    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Tasks");
        while (rs.next()) {
            tasks.add(new Task(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("assignedTo"),
                    rs.getInt("dayStart"),
                    rs.getInt("duration")
            ));
        }
        return tasks;
    }
    public void deleteTask(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
