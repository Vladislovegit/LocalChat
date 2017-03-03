package client;

import client.thread.ReceiverThread;
import client.thread.SenderThread;
import model.TextMessage;

import java.util.ArrayList;

public class Client {

    private ReceiverThread receiverThread;
    private SenderThread senderThread;

    private ArrayList<TextMessage> textMessages = new ArrayList<>();

    public Client(String name) {
        receiverThread = new ReceiverThread(name, this);
        senderThread = new SenderThread(name, this);
    }

    public void start() {
        new Thread(receiverThread).start();
        new Thread(senderThread).start();
    }

    synchronized public void addMessage(TextMessage message) {
        textMessages.add(message);
    }

    synchronized public TextMessage getMessage(Long messageId) {
        for (TextMessage message: textMessages) {
            if(message.getId() == messageId)
                return message;
        }
        return null;
    }
}
