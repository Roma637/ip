import java.util.Scanner.*;
import java.util.Arrays;
import java.io.IOException;

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

                switch (type) {
                case "T":
                    Todo todo = new Todo(description);
                    if (isDone) todo.setDone(true);
                    list.addTaskObject(todo);
                    break;

                case "D":
                    if (parts.length < 4) {
                        throw new TaskException("Deadline missing due date");
                    }
                    Deadline deadline = new Deadline(description, parts[3]);
                    if (isDone) deadline.setDone(true);
                    list.addTaskObject(deadline);
                    break;

                case "E":
                    if (parts.length < 4) {
                        throw new TaskException("Event missing time");
                    }
                    String[] timeComponents = parts[3].split(" ");
                    String date = timeComponents[0] + " " + timeComponents[1].split("-")[0];
                    String endTime = timeComponents[1].split("-")[1];
                    Event event = new Event(description, date, endTime);
                    if (isDone) event.setDone(true);
                    list.addTaskObject(event);
                    break;

                default:
                    throw new TaskException("Unknown task type: " + type);
                }
            }
        } catch (FileNotFoundException e) {
            throw new TaskException("Could not find file: " + filePath);
        }
        return list;
    }

    public static void WriteToFile(List l, String filePath) throws TaskException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            Task[] tasks = list.getTaskList();
//            int size = list.size();



            for (int i = 0; i < size; i++) {
                Task task = tasks[i];
                String line = "";

                if (task instanceof Todo) {
                    line = "T | ";
                } else if (task instanceof Deadline) {
                    line = "D | ";
                } else if (task instanceof Event) {
                    line = "E | ";
                }

                line += (task.isDone() ? "1" : "0") + " | ";
                line += task.getTaskName();

                if (task instanceof Deadline) {
                    line += " | " + ((Deadline) task).getDeadline();
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    line += " | " + event.getStart() + "-" + event.getEnd();
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

            case "todo":
            case "event":
            case "deadline":
                String fullArgument = String.join(" ", arguments);
//                if (fullArgument.equals("")) {
//                    Respond("Error: Please provide a valid task number");
//                } else {
                    Respond(l.addTask(fullArgument, command));
//                }
                break;

            default:
                Respond("Error: '" + command + "' is not a valid command");
            }
        } catch (TaskException e) {
            Respond("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Logo();
        Greet();

        String fp = "./data.txt" ;

        Scanner input = new Scanner(System.in);

        // read from file OR init fresh file
        List l = ReadFromFile(fp);

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
        WriteToFile(l, fp);

        Exit();
    }
}