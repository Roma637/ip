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

                if (line.equals("list")) {
                    Respond(l.displayTasks());
                } else if (line.startsWith("unmark")) {
                    // somehow grab the task index and -1 here
                    int index = (Integer.parseInt(line.substring(7).trim()) - 1);
                    System.out.println(index);
                    Respond(l.unmarkTask(index));
//                    Respond("i see you want me to UNmark this");
                } else if (line.startsWith("mark")) {
                    int index = (Integer.parseInt(line.substring(5).trim()) - 1);
                    System.out.println(index);
                    // somehow grab the task index and -1 here
                    Respond(l.markTask(index));
//                    Respond("i see you want me to mark this");
                } else {
                    Respond(l.addTask(line));
                }

                line = input.nextLine();
            }

        }

        Exit();
    }
}
