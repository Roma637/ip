import java.util.Scanner;
import java.util.Arrays;

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
//                    Respond(l.markTask(index));
                    Respond(l.deleteTask(index));

                } catch (NumberFormatException e) {
                    Respond("Error: Please provide a valid task number");
                } catch (ArrayIndexOutOfBoundsException e) {
                    Respond("Error: Please provide a task number to delete");
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

        Scanner input = new Scanner(System.in);
        List l = new List(new String[]{});
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

        Exit();
    }
}