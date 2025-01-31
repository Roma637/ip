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
//        System.out.println("---");
    }

    public static void Greet() { Respond("Oh. It's you. Hello."); }

    public static void Exit() {
        Respond("Fine, I'm leaving to find more lasagna.");
    }

    public static void main(String[] args) {
//        String logo = "GARFIELD";

        Logo();
        Greet();
//        System.out.println("---");

        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        boolean exit = false;

        do {
            if (line.equals("bye")) {
                exit = true;
            } else {
                Respond(line);
                line = input.nextLine();
            }

        } while (!exit);

        Exit();
    }
}
