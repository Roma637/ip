import java.util.Scanner;

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

        List l = new List(new String[]{});

        while (!exit) {

            if (line.equals("bye")) {
                exit = true;
            } else {
                switch(line) {
                    case "list":
                        Respond(l.displayTasks());
                        break;
                        default:
                            Respond(l.addTask(line));
                            break;
                }

                line = input.nextLine();
            }

        }

        Exit();
    }
}
