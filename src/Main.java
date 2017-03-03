import client.Client;
import model.*;

import java.util.Calendar;

public class Main {

    private static final String START_COMMAND_FORMAT = "start USERNAME";

    public static void main(String[] args) {
        new Client(String.valueOf(Calendar.getInstance().getTime().hashCode())).start();
    }

}
