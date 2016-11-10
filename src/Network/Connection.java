package Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by AUTOY on 2016/4/30.
 */
class Connection {
    public static void sendMulticastMessage(String message, MulticastSocket socket, InetAddress group, int port) throws IOException {
        System.out.println("send multicast:" + message);
        byte[] bytes = message.getBytes("UTF8");
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, group, port);
        socket.send(dp);
    }

    public static void sendDatagramMessage(String message, DatagramSocket socket, InetAddress address, int port) throws IOException {
        System.out.println("send datagram:" + address.getHostAddress() + "|" + port + ":" + message);
        byte[] bytes = message.getBytes("UTF8");
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, address, port);
        socket.send(dp);
    }
}


