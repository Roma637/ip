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

    public String addTask(String input, String taskType) {
        String description;
        Task newTask;

        switch (taskType) {
        case "todo":
            description = input.trim();
            newTask = new Todo(description);
            break;

        case "deadline":
            String[] deadlineParts = input.split("/by");
            if (deadlineParts.length < 2) {
                return "ERROR: Deadline requires a due date with /by";
            }
            description = deadlineParts[0].trim();
            String deadline = deadlineParts[1].trim();
            newTask = new Deadline(description, deadline);
            break;

        case "event":
            // First split by /from to get description
            String[] fromParts = input.split("/from");
            if (fromParts.length < 2) {
                return "ERROR: Event requires start time with /from";
            }
            description = fromParts[0].trim();

            // Then split remaining part by /to
            String[] toParts = fromParts[1].split("/to");
            if (toParts.length < 2) {
                return "ERROR: Event requires end time with /to";
            }
            String startTime = toParts[0].trim();
            String endTime = toParts[1].trim();
            newTask = new Event(description, startTime, endTime);
            break;

        default:
            newTask = new Task(input.trim());
            break;
        }

        taskList[size] = newTask;
        size++;
        return "added: " + newTask.toString();
    }

    public String markTask(int taskIndex) {
        if (taskIndex < size) {
            taskList[taskIndex].setDone(true);
            return "I've marked this as done: " + taskList[taskIndex].getTaskName();
        }
        return "ERROR. Index not in list";
    }

    public String unmarkTask(int taskIndex) {
        if (taskIndex < size) {
            taskList[taskIndex].setDone(false);
            return "I've unmarked this as done: " + taskList[taskIndex].getTaskName();
        }
        return "ERROR. Index not in list";
    }

    public String displayTasks() {
        String whole = "";
        for (int i = 0; i < size; i++) {
            whole += String.valueOf(i+1) + "." + taskList[i].toString() + "\n";
        }
        return whole;
    }
}