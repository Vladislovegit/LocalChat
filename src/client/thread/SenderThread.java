package client.thread;

import client.Client;
import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;

public class SenderThread implements Runnable {

    private InetAddress multicastAddress;
    private byte[] ipRequestData = new IpRequestPackage().toBytes();
    private TextMessagePackage textMessagePackage = new TextMessagePackage();
    private Client client;

    public SenderThread(Client client) {
        this.multicastAddress = getMulticastAddress();
        this.client = client;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            MulticastSocket socket = new MulticastSocket();
            socket.joinGroup(multicastAddress);
            while (true) {
                byte[] data;
                String inputLine = input.readLine();
                if(inputLine.toUpperCase().equals(Constant.IP_REQUEST_COMMAND)) {
                    data = ipRequestData;
                } else {
                    TextMessage message = new TextMessage(inputLine,
                            client.getLastReceivedMessageId() + 1,
                            client.getName(),
                            Calendar.getInstance().getTime());
                    client.addSandedMessage(message);
                    textMessagePackage.setData(message);
                    data = textMessagePackage.toBytes();
                }
                socket.send(new DatagramPacket(data, data.length, multicastAddress, Constant.MULTICAST_PORT));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}