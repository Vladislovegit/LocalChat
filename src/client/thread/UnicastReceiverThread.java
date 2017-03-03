package client.thread;

import client.Client;
import model.*;

import java.io.IOException;
import java.net.*;

public class UnicastReceiverThread implements Runnable {

    private Client client;

    public UnicastReceiverThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(Constant.UNICAST_PORT);
            byte[] buffer = new byte[Constant.BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(receivePacket);
                switch (PackageParser.getType(receivePacket.getData())) {
                    case Constant.IP_RESULT_PACKAGE:
                        IpResultPackage ipResultPackage = PackageParser.fromBytes(receivePacket.getData(), IpResultPackage.class);
                        System.out.println(ipResultPackage.getData().getName() + ": " + receivePacket.getAddress().getHostAddress());
                        break;
                    case Constant.TEXT_MESSAGE:
                        TextMessagePackage textMessagePackage = PackageParser.fromBytes(receivePacket.getData(), TextMessagePackage.class);
                        client.addReceivedMessages(textMessagePackage.getData());
                        System.out.println(textMessagePackage.getData().getOwner() + ": " + textMessagePackage.getData().getText());
                        break;
                    default:
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
