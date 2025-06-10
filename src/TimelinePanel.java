import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TimelinePanel extends JPanel {
    private List<Task> tasks;

    public TimelinePanel(List<Task> tasks) {
        this.tasks = tasks;
        setPreferredSize(new Dimension(800, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int startX = 50;
        int dayWidth = 40;

        // Draw base timeline
        g.drawLine(startX, 50, startX + dayWidth * 14, 50);
        for (int i = 0; i < 14; i++) {
            int x = startX + i * dayWidth;
            g.drawLine(x, 45, x, 55);
            g.drawString("Day " + (i + 1), x - 10, 70);
        }

        // Draw tasks
        int y = 100;
        for (Task task : tasks) {
            int x = startX + task.getDayStart() * dayWidth;
            int width = task.getDuration() * dayWidth;
            g.setColor(Color.RED);
            g.fillRect(x, y, width, 20);
            g.setColor(Color.BLACK);
            g.drawString(task.getName() + " (" + task.getAssignedTo() + ")", x, y - 5);
            y += 40;
        }
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
