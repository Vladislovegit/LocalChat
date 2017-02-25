import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Message {

    public static final long MESSAGE = 0;
    public static final long PING = 1;
    public static final long PONG = 2;

    private DatagramPacket packet;

    private Message(int size) {
        byte[] buffer = new byte[size + Long.BYTES];
        packet = new DatagramPacket(buffer, buffer.length);
    }

    private Message(long code, byte[] data, int dataLength, InetAddress address, int port) {
        byte[] encapsulatedData = new byte[dataLength + Long.BYTES];
        System.arraycopy(ByteBuffer.allocate(Long.BYTES).putLong(code).array(), 0, encapsulatedData, 0, Long.BYTES);
        System.arraycopy(data, 0, encapsulatedData, Long.BYTES, dataLength);
        packet = new DatagramPacket(encapsulatedData, dataLength + Long.BYTES, address, port);
    }

    public byte[] getData() {
        return Arrays.copyOfRange(packet.getData(), Long.BYTES, packet.getLength());
    }

    public String getDataAsString() {
        byte[] data = getData();
        return (new String(data, 0, data.length)).trim();
    }

    public long getCode() {
        ByteBuffer numberBytes = ByteBuffer.allocate(Long.BYTES)
                .put(Arrays.copyOfRange(packet.getData(), 0, Long.BYTES));
        numberBytes.flip();
        return numberBytes.getLong();
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public static DatagramPacket getPacketForSend(long code, byte[] data, int dataLength,
                                                  InetAddress address, int port) {
        return (new Message(code, data, dataLength, address, port)).packet;
    }

    public static Message getPacketForReceive(int size) {
        return new Message(size);
    }
}
