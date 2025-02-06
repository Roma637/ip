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
                "\n" ;
        System.out.println("---");
        System.out.println("Summoning\n" + logo);
    }

    public static void Greet() { Respond("Oh. It's you. Hello. What do you want?"); }

    public static void Exit() {
        Respond("Fine, I'm leaving to find more lasagna.");
    }

    public static void main(String[] args) {

        Logo();
        Greet();

        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        boolean exit = false;
        int index ;

        List l = new List(new String[]{});

        while (!exit) {

            String trimmedLine = line.trim() ;

            if (trimmedLine.equals("bye")) {
                exit = true;
            } else {

                String[] parts = trimmedLine.split("\\s+", 2) ;
                String command = parts[0] ;
                String[] arguments = (parts.length > 1) ? Arrays.copyOfRange(parts, 1, parts.length) : new String[0] ;

                switch (command) {
                case "list":
                    Respond(l.displayTasks());
                    break;
                case "unmark":
                    index = (Integer.parseInt(arguments[0].trim()) - 1);
//                    System.out.println(index);
                    Respond(l.unmarkTask(index));
                    break;
                case "mark":
                    index = (Integer.parseInt(arguments[0].trim()) - 1);
//                    System.out.println(index);
                    Respond(l.markTask(index));
                    break;
                case "todo":
                case "event":
                case "deadline":
                    String fullArgument = String.join(" ", arguments);
                    Respond(l.addTask(fullArgument, command));
                    break;
                default:
                    Respond(l.addTask(trimmedLine, "default"));
                }

                line = input.nextLine();

            }

        }

        Exit();
    }
}
