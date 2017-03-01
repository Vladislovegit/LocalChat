package client;

import client.thread.ReceiverThread;
import client.thread.SenderThread;

public class Client {

    private ReceiverThread receiverThread;
    private SenderThread senderThread;

    public Client(String name, String interfaceName) {
        receiverThread = new ReceiverThread(name, interfaceName);
        senderThread = new SenderThread(name, interfaceName);
    }

    public void start() {
        new Thread(receiverThread).start();
        new Thread(senderThread).start();
    }
}
