package client.thread;

import client.Client;
import model.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class MulticastReceiverThread implements Runnable {

    private InetAddress multicastAddress;
    private Client client;

    public MulticastReceiverThread(Client client) {
        this.multicastAddress = getMulticastAddress();
        this.client = client;
    }

    @Override
    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket(Constant.MULTICAST_PORT);
            socket.joinGroup(multicastAddress);
            byte[] buffer = new byte[Constant.BUFFER_SIZE];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            byte[] ipResult = new IpResultPackage(new IpResult(client.getName())).toBytes();
            while (true) {
                socket.receive(receivePacket);
                switch (PackageParser.getType(receivePacket.getData())) {
                    case Constant.IP_REQUEST_PACKAGE:
                        socket.send(new DatagramPacket(ipResult, ipResult.length, receivePacket.getAddress(), Constant.UNICAST_PORT));
                        break;
                    case Constant.TEXT_MESSAGE:
                        TextMessagePackage message = PackageParser.fromBytes(receivePacket.getData(), TextMessagePackage.class);
                        System.out.println(message.getData().getOwner() + ": " + message.getData().getText());
                        if(client.getLastReceivedMessageId() + 1 < message.getData().getId()) {
                            ArrayList<Long> requestedMessagesIds = new ArrayList<>();
                            for (int i = 0; i < 20; i++) {
                                Long id = message.getData().getId() - 30;
                                if(id >= 0 && client.getReceivedMessage(id) == null) {
                                    requestedMessagesIds.add(id);
                                }
                            }
                            byte[] data = new MessagesRequestPackage(new MessagesRequest(requestedMessagesIds)).toBytes();
                            socket.send(new DatagramPacket(data, data.length, multicastAddress, Constant.MULTICAST_PORT));
                        }
                        client.addReceivedMessages(message.getData());
                    case Constant.MESSAGES_REQUEST_PACKAGE:
                        MessagesRequestPackage messagesRequestPackage = PackageParser.fromBytes(receivePacket.getData(), MessagesRequestPackage.class);
                        for (Long requestedMessageId: messagesRequestPackage.getData().getMessagesIds()) {
                            TextMessage sandedMessage = client.getSandedMessage(requestedMessageId);
                            if(sandedMessage != null) {
                                byte[] bytes = new TextMessagePackage(sandedMessage).toBytes();
                                socket.send(new DatagramPacket(bytes, bytes.length, receivePacket.getAddress(), Constant.UNICAST_PORT));
                            }
                        }
                        break;
                    default:
                        System.out.println("Unknown package: " + new String(receivePacket.getData()));
                }
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