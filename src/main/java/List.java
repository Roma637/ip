import java.util.ArrayList;

public class List {
    private ArrayList<Task> taskList;

    public List() {
        this(new String[]{});
    }

    public List(String[] args) {
        int size = args.length;
        this.taskList = new ArrayList<Task>();

        for (int i = 0; i < size; i++) {
            this.taskList.add(new Task(args[i]));
        }
    }

    public int getSize() {
        return taskList.size();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public String addTask(Task task) throws TaskException {
        taskList.add(task);
        return "Another task to do? Ugh. Writing it down: " + task.toString();
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

        taskList.add(newTask);
        return "Another task to do? Ugh. Writing it down: " + newTask.toString();
    }

    public String markTask(int taskIndex) throws TaskException {
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            throw new TaskException("Can you count? That task index doesn't exist: " + (taskIndex + 1));
        }

        Task toMark = taskList.get(taskIndex);
        toMark.setDone(true);
        taskList.set(taskIndex, toMark);

        return "Another one down: " + taskList.get(taskIndex).getTaskName();
    }

    public String unmarkTask(int taskIndex) throws TaskException {
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            throw new TaskException("Can you count? That task index doesn't exist: " + (taskIndex + 1));
        }

        Task toUnmark = taskList.get(taskIndex);
        toUnmark.setDone(false);
        taskList.set(taskIndex, toUnmark);

        return "So you lied to me? Marking this task as undone: " + taskList.get(taskIndex).getTaskName();
    }

    public String deleteTask(int taskIndex) throws TaskException {
        if (taskIndex < 0 || taskIndex >= taskList.size()) {
            throw new TaskException("Can you count? That task index doesn't exist: " + (taskIndex + 1));
        }
        String repond = "Go away task! " + taskList.get(taskIndex).getTaskName();

        taskList.remove(taskIndex);

        return repond;
    }

    public String displayTasks() throws TaskException {
        if (taskList.size() == 0) {
            throw new TaskException("You've finished all your tasks. Incredible.");
        }

        StringBuilder whole = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            whole.append(String.valueOf(i + 1))
                    .append(".")
                    .append(taskList.get(i).toString())
                    .append("\n");
        }
        return whole.toString();
    }
}