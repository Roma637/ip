import java.io.IOException;

public class List {
    private Task[] taskList;
    private int size;

    public List() {
        this(new String[]{});
    }

    public List(String[] args) {
        this.size = args.length;
        this.taskList = new Task[100];

        for (int i = 0; i < size; i++) {
            this.taskList[i] = new Task(args[i]);
        }
    }

    public int getSize() {
        return this.size;
    }

    public String addTask(String input, String taskType) throws TaskException {
        String description;
        Task newTask;

        if (input.equals("")) {
            return "Error: input cannot be empty, just like my stomach";
        }

        switch (taskType) {
        case "todo":
            description = input.trim();
            newTask = new Todo(description);
            break;

        case "deadline":
            String[] deadlineParts = input.split("/by");
            if (deadlineParts.length < 2) {
                throw new TaskException("Deadline requires a due date with /by");
            }
            description = deadlineParts[0].trim();
            String deadline = deadlineParts[1].trim();
            newTask = new Deadline(description, deadline);
            break;

        case "event":
            String[] fromParts = input.split("/from");
            if (fromParts.length < 2) {
                throw new TaskException("Event requires start time with /from");
            }
            description = fromParts[0].trim();

            String[] toParts = fromParts[1].split("/to");
            if (toParts.length < 2) {
                throw new TaskException("Event requires end time with /to");
            }
            String startTime = toParts[0].trim();
            String endTime = toParts[1].trim();
            newTask = new Event(description, startTime, endTime);
            break;

        default:
            newTask = new Task(input.trim());
            break;
        }

        if (size >= taskList.length) {
            throw new TaskException("You have too many tasks, what's wrong with you?");
        }

        taskList[size] = newTask;
        size++;
        return "Another task to do? Ugh. Writing it down: " + newTask.toString();
    }

    public String markTask(int taskIndex) throws TaskException {
        if (taskIndex < 0 || taskIndex >= size) {
            throw new TaskException("Can you count? That task index doesn't exist: " + (taskIndex + 1));
        }
        taskList[taskIndex].setDone(true);
        return "Another one down: " + taskList[taskIndex].getTaskName();
    }

    public String unmarkTask(int taskIndex) throws TaskException {
        if (taskIndex < 0 || taskIndex >= size) {
            throw new TaskException("Can you count? That task index doesn't exist: " + (taskIndex + 1));
        }
        taskList[taskIndex].setDone(false);
        return "So you lied to me? Marking this task as undone: " + taskList[taskIndex].getTaskName();
    }

    public String displayTasks() throws TaskException {
        if (size == 0) {
            throw new TaskException("You've finished all your tasks. Incredible.");
        }

        StringBuilder whole = new StringBuilder();
        for (int i = 0; i < size; i++) {
            whole.append(String.valueOf(i + 1))
                    .append(".")
                    .append(taskList[i].toString())
                    .append("\n");
        }
        return whole.toString();
    }
}