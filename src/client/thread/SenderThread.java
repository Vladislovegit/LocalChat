package client.thread;

import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class SenderThread implements Runnable {

    private final String name;
    private InetAddress multicastAddress;
    private byte[] ipRequestData = new IpRequestPackage().toBytes();
    private TextMessagePackage textMessagePackage = new TextMessagePackage();

    public SenderThread(String name) {
        this.name = name;
        this.multicastAddress = getMulticastAddress();
    }

    private InetAddress getMulticastAddress() {
        InetAddress multicast = null;
        try {
            multicast = InetAddress.getByName(Constant.multicastAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return multicast;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            MulticastSocket socket = new MulticastSocket(Constant.MULTICAST_PORT);
            socket.joinGroup(multicastAddress);
            while (true) {
                byte[] data;
                String inputLine = input.readLine();
                if(inputLine.toUpperCase().equals(Constant.IP_REQUEST_COMMAND)) {
                    data = ipRequestData;
                } else {
                    textMessagePackage.setData(new TextMessage(inputLine, 123L, name));
                    data = textMessagePackage.toBytes();
                }
                socket.send(new DatagramPacket(data, data.length, multicastAddress, Constant.MULTICAST_PORT));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}