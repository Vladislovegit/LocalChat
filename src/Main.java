import client.Client;
import model.*;

public class Main {

    private static final String START_COMMAND_FORMAT = "start USERNAME INTERFACE-NAME";

    public static void main(String[] args) {
        if (args.length < 1) System.out.println("No arguments: " + START_COMMAND_FORMAT);
        else (new Client(args[0], args[1])).start();
        TextMessagePackage aCustomPackage = new TextMessagePackage(new TextMessage("", 10L));
        IpRequestPackage bCustomPackage = new IpRequestPackage();

        System.out.println(new String(aCustomPackage.toBytes()));
        System.out.println(new String(bCustomPackage.toBytes()));
    }

    public static class A {
        private Integer a = 0;

        public A() {}
    }

    public static class B {
        private Integer a = 0;
        private Integer b = -1;

        public B() {}
    }
}
