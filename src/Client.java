import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Random;

public class Client {

    private static final String PING_COMMAND = "PING";
    private static final int DEFAULT_SENDER_PORT = 59594;
    private static final int DEFAULT_RECEIVER_PORT = 59595;
    private static final int DEFAULT_PACKET_SIZE = 1024;
    private static InetAddress broadcast;
    private static String pongMessage;

    public static void main(String[] args) throws SocketException, UnknownHostException {
        String name;
        String interfaceName;
        name = args.length > 0 ? args[0] : "user" + new Random().nextInt(0xFFFF);
        interfaceName = args.length > 1 ? args[1] : NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getName();
        NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
        broadcast = networkInterface.getInterfaceAddresses().get(0).getBroadcast();
        pongMessage = name + " (" + networkInterface.getInterfaceAddresses().get(0).getAddress().getHostAddress() + ") is here!";
        new Thread(new ReceiverThread()).start();
        new Thread(new SenderThread(name)).start();
    }

    private static class ReceiverThread implements Runnable {

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(DEFAULT_RECEIVER_PORT);
                socket.setBroadcast(true);
                while (true) {
                    Message message = Message.getPacketForReceive(DEFAULT_PACKET_SIZE);
                    socket.receive(message.getPacket());
                    if(message.getCode() == Message.MESSAGE || message.getCode() == Message.PONG) {
                        System.out.println(message.getDataAsString());
                    } else if (message.getCode() == Message.PING) {
                        socket.send(Message.getPacketForSend(Message.PONG, pongMessage.getBytes(),
                                pongMessage.length(), message.getPacket().getAddress(), DEFAULT_RECEIVER_PORT));
                    }
                }
            } catch (IOException ignored) {}
        }
    }

    private static class SenderThread implements Runnable {

        String name;

        SenderThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                DatagramSocket socket = new DatagramSocket(DEFAULT_SENDER_PORT);
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    String line = input.readLine();
                    String message = name + ": " + line;
                    final long code = line.toUpperCase().equals(PING_COMMAND) ? Message.PING : Message.MESSAGE;
                    socket.send(Message.getPacketForSend(code, message.getBytes(),
                            message.length(), broadcast, DEFAULT_RECEIVER_PORT));
                }
            } catch (IOException ignored) {}
        }
    }
}
