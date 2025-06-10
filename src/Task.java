public class Task {
    private int id;
    private String name;
    private String assignedTo;
    private int dayStart;
    private int duration;

    public Task(int id, String name, String assignedToo, int dayStart, int duration) {
        this.id = id;
        this.name = name;
        this.assignedTo = assignedToo;
        this.dayStart = dayStart;
        this.duration = duration;

    }
    public int getId() {
        return id;

    }
    public String getName() {
        return name;
    }
    public String getAssignedTo() {
        return assignedTo;
    }
    public int getDayStart() {
        return dayStart;
    }
    public int getDuration() {
        return duration;
    }
    @Override
    public String toString() {
        return name + " (" + assignedTo + ", starts Day " + dayStart + ", duration " + duration + ")";
    }



}
