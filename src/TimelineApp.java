import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TimelineApp {
    private static TimelinePanel timelinePanel;
    private static JList<Task> taskList;
    private static DefaultListModel<Task> listModel;

    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        TaskDatabase db = new TaskDatabase("tasks.db");

        JFrame frame = new JFrame("Timeline Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Timeline
        timelinePanel = new TimelinePanel(db.getAllTasks());
        frame.add(timelinePanel, BorderLayout.CENTER);

        // Form
        JPanel inputPanel = new JPanel(new GridLayout(2, 5));
        JTextField nameField = new JTextField();
        JTextField assignedField = new JTextField();
        JTextField dayStartField = new JTextField();
        JTextField durationField = new JTextField();
        JButton addButton = new JButton("Add Task");

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(new JLabel("Assigned To:"));
        inputPanel.add(new JLabel("Start Day:"));
        inputPanel.add(new JLabel("Duration:"));
        inputPanel.add(new JLabel(""));

        inputPanel.add(nameField);
        inputPanel.add(assignedField);
        inputPanel.add(dayStartField);
        inputPanel.add(durationField);
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.SOUTH);

        // Task List + Delete
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        refreshTaskList(db);

        JButton deleteButton = new JButton("Delete Selected Task");
        deleteButton.addActionListener(e -> {
            Task selected = taskList.getSelectedValue();
            if (selected != null) {
                try {
                    db.deleteTask(selected.getId());
                    refreshTimeline(db);
                    refreshTaskList(db);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        sidePanel.add(deleteButton, BorderLayout.SOUTH);
        frame.add(sidePanel, BorderLayout.EAST);

        // Add Button Logic
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String assigned = assignedField.getText();
                int start = Integer.parseInt(dayStartField.getText());
                int duration = Integer.parseInt(durationField.getText());

                db.addTask(new Task(0, name, assigned, start, duration));
                nameField.setText("");
                assignedField.setText("");
                dayStartField.setText("");
                durationField.setText("");
                refreshTimeline(db);
                refreshTaskList(db);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input: " + ex.getMessage());
            }
        });

        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

    private static void refreshTimeline(TaskDatabase db) {
        try {
            timelinePanel.setTasks(db.getAllTasks());
            timelinePanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void refreshTaskList(TaskDatabase db) {
        try {
            listModel.clear();
            for (Task task : db.getAllTasks()) {
                listModel.addElement(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
