import client.Client;

public class Main {

    private static final String START_COMMAND_FORMAT = "start USERNAME INTERFACE-NAME";

    public static void main(String[] args) {
        if (args.length < 1) System.out.println("No arguments: " + START_COMMAND_FORMAT);
        else (new Client(args[0], args[1])).start();

    }
}
