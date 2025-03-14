import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class List {
    private ArrayList<Task> taskList;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

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

            Deadline deadlineTask = new Deadline(description, deadline);
            // Store original string for compatibility

            deadlineTask.setDeadlineString(deadline);

            newTask = deadlineTask;
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

            Event eventTask = new Event(description, startTime, endTime);
            // Store original strings for compatibility

            eventTask.setStartString(startTime);
            eventTask.setEndString(endTime);

            newTask = eventTask;
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
        if (taskList.isEmpty()) {
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

    /**
     * Searches for tasks containing a keyword in their description
     * @param keyword The keyword to search for
     * @return A string with the matching tasks
     * @throws TaskException If no tasks match the search criteria
     */
    public String searchTasks(String keyword) throws TaskException {
        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getTaskName().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            throw new TaskException("No tasks found matching '" + keyword + "'.");
        }

        StringBuilder result = new StringBuilder();
        result.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            result.append(String.valueOf(i + 1))
                    .append(".")
                    .append(matchingTasks.get(i).toString())
                    .append("\n");
        }

        return result.toString();
    }
}