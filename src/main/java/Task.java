public class Task {
    private String taskName;
    private boolean done;

    public Task(String taskName, boolean done) {
        this.taskName = taskName;
        this.done = done;
    }

    public Task(String taskName) {
        this(taskName, false);
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone() {
        this.done = true;
    }

}