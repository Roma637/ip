public class Task {
    protected String taskName;
    protected String taskType;
    protected boolean done;

    public Task(String taskName, boolean done) {
        this.taskName = taskName;
        this.taskType = "default";
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

    public void setDone(boolean done) {
        this.done = done;
    }

    public String toString() {
        String a = "[" + taskType + "][" ;
        if (done) {a += "X";} else {a += " ";}
        a += "] " + taskName ;
        return a;
    }
}