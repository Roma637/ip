public class Garfield {

    public static void Greet() {
        System.out.println("Oh. It's you. Hello.");
    }

    public static void Exit() {
        System.out.println("I'm leaving to find more lasagna.");
    }

    public static void main(String[] args) {
//        String logo = "GARFIELD";
        String logo = "  ____   ____  ____   _____  ____    ___  _      ___   \n" +
                " /    | /    ||    \\ |     ||    |  /  _]| |    |   \\  \n" +
                "|   __||  o  ||  D  )|   __| |  |  /  [_ | |    |    \\ \n" +
                "|  |  ||     ||    / |  |_   |  | |    _]| |___ |  D  |\n" +
                "|  |_ ||  _  ||    \\ |   _]  |  | |   [_ |     ||     |\n" +
                "|     ||  |  ||  .  \\|  |    |  | |     ||     ||     |\n" +
                "|___,_||__|__||__|\\_||__|   |____||_____||_____||_____|\n" +
                "\n" ;
        System.out.println("---");
        System.out.println("Hello from\n" + logo);

        System.out.println("---");
        Greet();
        System.out.println("---");
        Exit();
    }
}
