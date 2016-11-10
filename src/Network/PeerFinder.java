package Network;

import Definition.ClientInformation;
import Definition.PeerFinderMessage;
import Definition.ServerInformation;
import MainClass.TheApp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by AUTOY on 2016/5/5.
 */
public class PeerFinder {
    private final int PeerFinderStart = 13000;
    private final String multicastAddress = "225.0.0.1";
    private PeerFinderThread AnswerThread;
    private MulticastSocket socket;
    private InetAddress group;
    private int multiPort;
    private TheApp theApp;
    private ServerInformation server;
    private ClientInformation client;

    public PeerFinder(TheApp theApp) {
        multiPort = PeerFinderStart;
        this.theApp = theApp;
        client = theApp.getMyClient();
        while (!trySocket(multiPort)) {
            multiPort++;
        }
        try {
            group = InetAddress.getByName(multicastAddress);
            socket.joinGroup(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * this function is to dynamically allocate multiPort to MulticastSocket simply by try out each possible multiPort
     *
     * @param port is the multiPort number
     * @return whether this operation has been done;
     */
    private boolean trySocket(int port) {
        try {
            socket = new MulticastSocket(port);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void startAnswering(RoomServer server) {
        this.server = server.getServerInformation();
        AnswerThread = new PeerFinderThread(this, server);
        AnswerThread.start();
    }


    MulticastSocket getSocket() {
        return socket;
    }

    void sendMulticastMessage(String message) throws IOException {
        Connection.sendMulticastMessage(message, socket, group, multiPort);
    }

    public void sendKnockMessage() {
        String message = PeerFinderMessage.KNOCK + "|" + client.clientAddress.getHostAddress() + "|" + multiPort;
        try {
            sendMulticastMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    String getAddressString() {
        return client.clientAddress.getHostAddress();
    }


    void handlePeerChange(String name, String address, String port) {
        theApp.handlePeerChange(name, address, port);
    }

    void handlePeerLeave(String address, String port) {
        theApp.handlePeerLeave(address + "|" + port);
    }


    public void sendLeaveMessage() {
        String message = PeerFinderMessage.LEAVE + "|" + client.clientAddress.getHostAddress() + "|" + server.serverPort;
        try {
            sendMulticastMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
