package model;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MessageOldEugen {

    private static final int DEFAULT_PACKET_SIZE = 1024;

    public static final long MESSAGE = -5;
    public static final long REQUEST = -105;

    private static final int CODE_SIZE = Long.BYTES;

    private DatagramPacket packet;

    private MessageOldEugen() {
        int size = CODE_SIZE + DEFAULT_PACKET_SIZE;
        byte[] buffer = new byte[size];
        packet = new DatagramPacket(buffer, buffer.length);
    }

    private MessageOldEugen(long code, byte[] data, int dataLength, InetAddress address, int port) {
        byte[] encapsulatedData = new byte[CODE_SIZE + dataLength];

        byte[] codeArray = ByteBuffer.allocate(CODE_SIZE).putLong(code).array();

        System.arraycopy(codeArray, 0, encapsulatedData, 0, CODE_SIZE);

        System.arraycopy(data, 0, encapsulatedData, CODE_SIZE, dataLength);

        packet = new DatagramPacket(encapsulatedData, CODE_SIZE + dataLength, address, port);
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public static DatagramPacket getPacketForSend(long code, String data, InetAddress address, int port) {
        return (new MessageOldEugen(code, data.getBytes(), data.length(), address, port)).packet;
    }

    public static MessageOldEugen getPacketForReceive() {
        return new MessageOldEugen();
    }

    public byte[] getData() {
        return Arrays.copyOfRange(packet.getData(), CODE_SIZE, packet.getLength());
    }

    public long getCode() {
        ByteBuffer numberBytes = ByteBuffer.allocate(CODE_SIZE).put(Arrays.copyOfRange(packet.getData(), 0, CODE_SIZE));
        numberBytes.flip();
        return numberBytes.getLong();
    }

    public String toString() {
        byte[] data = getData();
        return (new String(data, 0, data.length)).trim();
    }
}
