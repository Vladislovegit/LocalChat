package client;

import client.thread.MulticastReceiverThread;
import client.thread.SenderThread;
import client.thread.UnicastReceiverThread;
import model.TextMessage;

import java.util.ArrayList;

public class Client {

    private String name;
    private MulticastReceiverThread multicastReceiverThread;
    private UnicastReceiverThread unicastReceiverThread;
    private SenderThread senderThread;

    private ArrayList<TextMessage> sandedMessages = new ArrayList<>();
    private ArrayList<TextMessage> receivedMessages = new ArrayList<>();
    private Long lastReceivedMessageId = 0L;

    public Client(String name) {
        this.name = name;
        multicastReceiverThread = new MulticastReceiverThread(this);
        unicastReceiverThread = new UnicastReceiverThread(this);
        senderThread = new SenderThread(this);
    }

    public void start() {
        new Thread(multicastReceiverThread).start();
        new Thread(senderThread).start();
        new Thread(unicastReceiverThread).start();
    }

    synchronized public void addSandedMessage(TextMessage message) {
        sandedMessages.add(message);
    }

    synchronized public TextMessage getSandedMessage(Long messageId) {
        for (TextMessage sandedMessage: sandedMessages) {
            if(sandedMessage.getId().equals(messageId))
                return sandedMessage;
        }
        return null;
    }

    synchronized public void addReceivedMessages(TextMessage message) {
        receivedMessages.add(message);
        setLastReceivedMessageId(message.getId());
    }

    synchronized public TextMessage getReceivedMessage(Long messageId) {
        for (TextMessage sandedMessage: receivedMessages) {
            if(sandedMessage.getId().equals(messageId))
                return sandedMessage;
        }
        return null;
    }

    synchronized public void setLastReceivedMessageId(Long id) {
        if(lastReceivedMessageId < id)
            lastReceivedMessageId = id;
    }

    synchronized public Long getLastReceivedMessageId() {
        return lastReceivedMessageId;
    }

    public String getName() {
        return name;
    }
}
