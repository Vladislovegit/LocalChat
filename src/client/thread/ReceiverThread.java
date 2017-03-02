package client.thread;

import model.Constant;
import model.MessageOldEugen;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.NetworkInterface;
import java.net.SocketException;

public class ReceiverThread implements Runnable {
    private final String name;
    private final String interfaceName;

    private String address;

    public ReceiverThread(String name, String interfaceName) {
        this.name = name;
        this.interfaceName = interfaceName;
        this.address = getAddress();
    }

    private String getAddress() {
        String address = "";
        try {

            NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
            address = networkInterface.getInterfaceAddresses().get(0).getAddress().getHostAddress();

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(Constant.RECEIVER_PORT)) {
            socket.setBroadcast(true);
            MessageOldEugen message = MessageOldEugen.getPacketForReceive();

            while (true) {
                socket.receive(message.getPacket());

                if (message.getCode() == MessageOldEugen.MESSAGE) {
                    System.out.println(message.toString());
                } else if (message.getCode() == MessageOldEugen.REQUEST) {

                    String reply = name + " [" + address + "].";

                    socket.send(
                            MessageOldEugen.getPacketForSend(
                                    MessageOldEugen.MESSAGE,
                                    reply,
                                    message.getPacket().getAddress(),
                                    Constant.RECEIVER_PORT
                            )
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}