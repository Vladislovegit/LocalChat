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

    private final String name;
    private InetAddress multicastAddress;
    private byte[] ipRequestData = new IpRequestPackage().toBytes();
    private TextMessagePackage textMessagePackage = new TextMessagePackage();
    private Client client;

    public SenderThread(String name, Client client) {
        this.name = name;
        this.multicastAddress = getMulticastAddress();
        this.client = client;
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
                    TextMessage textMessage = new TextMessage(inputLine, 123L, name, Calendar.getInstance().getTime());
                    textMessagePackage.setData(textMessage);
                    data = textMessagePackage.toBytes();
                }
                socket.send(new DatagramPacket(data, data.length, multicastAddress, Constant.MULTICAST_PORT));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}