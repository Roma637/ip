import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Garfield {

    public static void Respond(String sentence) {
        System.out.println("------------------");
        System.out.print("\t");
        System.out.println(sentence);
        System.out.println("------------------");
    }

    public static void Logo() {
        String logo = "  ____   ____  ____   _____  ____    ___  _      ___   \n" +
                " /    | /    ||    \\ |     ||    |  /  _]| |    |   \\  \n" +
                "|   __||  o  ||  D  )|   __| |  |  /  [_ | |    |    \\ \n" +
                "|  |  ||     ||    / |  |_   |  | |    _]| |___ |  D  |\n" +
                "|  |_ ||  _  ||    \\ |   _]  |  | |   [_ |     ||     |\n" +
                "|     ||  |  ||  .  \\|  |    |  | |     ||     ||     |\n" +
                "|___,_||__|__||__|\\_||__|   |____||_____||_____||_____|\n" +
                "\n";
        System.out.println("---");
        System.out.println("Summoning\n" + logo);
    }

    public static void Greet() {
        Respond("Oh. It's you. Hello. What do you want?");
    }

    public static void Exit() {
        Respond("Fine, I'm leaving to find more lasagna.");
    }

    public static List ReadFromFile(String filePath) throws TaskException {
        List list = new List();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(" \\| ");
                if (parts.length < 3) {
                    throw new TaskException("Invalid file format");
                }

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task = null;
                switch (type) {
                case "T":
                    task = new Todo(description);
                    break;

                case "D":
                    if (parts.length < 4) {
                        throw new TaskException("Deadline missing due date");
                    }
                    Deadline deadlineTask = new Deadline(description, parts[3]);
                    // Keep original string for compatibility
                    deadlineTask.setDeadline(parts[3]);
                    task = deadlineTask;
                    break;

                case "E":
                    if (parts.length < 5) {
                        throw new TaskException("Event missing start or end time");
                    }
                    Event eventTask = new Event(description, parts[3], parts[4]);
                    // Keep original strings for compatibility
                    eventTask.setStart(parts[3]);
                    eventTask.setEnd(parts[4]);
                    task = eventTask;
                    break;

                default:
                    throw new TaskException("Unknown task type: " + type);
                }

                if (isDone) task.setDone(true);
                list.addTask(task);
            }
        } catch (FileNotFoundException e) {
            throw new TaskException("Could not find file: " + filePath);
        } catch (Exception e) {
            throw new TaskException("Error reading from file: " + e.getMessage());
        }
        return list;
    }

    public static void WriteToFile(List l, String filePath) throws TaskException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            ArrayList<Task> tasks = l.getTaskList();
            int size = l.getSize();

            for (int i = 0; i < size; i++) {
                Task task = tasks.get(i);
                StringBuilder line = new StringBuilder();

                // Add type
                if (task instanceof Todo) {
                    line.append("T | ");
                } else if (task instanceof Deadline) {
                    line.append("D | ");
                } else if (task instanceof Event) {
                    line.append("E | ");
                }

                // Add done status
                line.append(task.isDone() ? "1" : "0").append(" | ");

                // Add task name
                line.append(task.getTaskName());

                // Add deadline or event times
                if (task instanceof Deadline) {
                    line.append(" | ").append(((Deadline) task).getDeadline());
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    line.append(" | ").append(event.getStart());
                    line.append(" | ").append(event.getEnd());
                }

                writer.println(line);
            }
        } catch (IOException e) {
            throw new TaskException("Error writing to file: " + e.getMessage());
        }
    }

    public static void handleCommand(List l, String command, String[] arguments) {
        try {
            int index;
            switch (command) {
            case "list":
                Respond(l.displayTasks());
                break;

            case "unmark":
                try {
                    index = Integer.parseInt(arguments[0].trim()) - 1;
                    Respond(l.unmarkTask(index));
                } catch (NumberFormatException e) {
                    Respond("Error: Please provide a valid task number");
                } catch (ArrayIndexOutOfBoundsException e) {
                    Respond("Error: Please provide a task number to unmark");
                }
                break;

            case "mark":
                try {
                    index = Integer.parseInt(arguments[0].trim()) - 1;
                    Respond(l.markTask(index));
                } catch (NumberFormatException e) {
                    Respond("Error: Please provide a valid task number");
                } catch (ArrayIndexOutOfBoundsException e) {
                    Respond("Error: Please provide a task number to mark");
                }
                break;

            case "delete":
                try {
                    index = Integer.parseInt(arguments[0].trim()) - 1;
                    Respond(l.deleteTask(index));

                } catch (NumberFormatException e) {
                    Respond("Error: Please provide a valid task number");
                } catch (ArrayIndexOutOfBoundsException e) {
                    Respond("Error: Please provide a task number to delete");
                }
                break;

            case "find":
                if (arguments.length == 0 || arguments[0].trim().isEmpty()) {
                    Respond("Error: Please provide a keyword to search for");
                } else {
                    String keyword = String.join(" ", arguments);
                    Respond(l.searchTasks(keyword));
                }
                break;

            case "todo":
            case "event":
            case "deadline":
                String fullArgument = String.join(" ", arguments);
                Respond(l.addTask(fullArgument, command));
                break;

            case "help":
                Respond("What, you don't know how this works? \n" +
                        "Available commands:\n" +
                        "list \n" +
                        "mark and unmark to mark and unmark tasks\n" +
                        "delete to delete a task\n" +
                        "find [keyword] to search for tasks\n" +
                        "todo, deadline and event followed by information to actually add a task");
                break;

            default:
                Respond(command + " is not a valid command. Type 'help' to see what is, dummy.");
            }
        } catch (TaskException e) {
            Respond("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Logo();
        Greet();

        String fp = "src/main/java/data.txt" ;

        Scanner input = new Scanner(System.in);

        List l = new List();

        // read from file OR init fresh file
        try {
            l = ReadFromFile(fp);
        } catch (TaskException e) {
            Respond("Sorry, reading from the file went wrong:" + e.getMessage());
            return;
        }

        boolean exit = false;

        while (!exit) {
            String line = input.nextLine();
            String trimmedLine = line.trim();

            if (trimmedLine.equals("bye")) {
                exit = true;
            } else {
                String[] parts = trimmedLine.split("\\s+", 2);
                String command = parts[0];
                String[] arguments = (parts.length > 1) ? parts[1].split("\\s+") : new String[0];

                handleCommand(l, command, arguments);
            }
        }

        // write to file
        try {
            WriteToFile(l, fp);
        } catch (TaskException e) {
            Respond("Sorry, something went wrong.");
            return;
        }

        Exit();
    }
}