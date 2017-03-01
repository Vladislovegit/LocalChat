package client.thread;

import model.Constant;
import model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

public class SenderThread implements Runnable {

    private final String name;
    private final String interfaceName;

    private InetAddress broadcastAddress;

    public SenderThread(String name, String interfaceName) {
        this.name = name;
        this.interfaceName = interfaceName;
        this.broadcastAddress = getBroadcastAddress();
    }

    private InetAddress getBroadcastAddress() {
        InetAddress broadcast = null;
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
            broadcast = networkInterface.getInterfaceAddresses().get(0).getBroadcast();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return broadcast;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                DatagramSocket socket = new DatagramSocket()) {

            while (true) {
                String inputLine = input.readLine();
                final long code = inputLine.toUpperCase().equals(Constant.IP_REQUEST_COMMAND) ? Message.REQUEST : Message.MESSAGE;

                String message = name + ": " + inputLine;
                socket.send(
                        Message.getPacketForSend(
                                code,
                                message,
                                broadcastAddress,
                                Constant.RECEIVER_PORT
                        )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}