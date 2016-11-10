package Network;


import Definition.ClientInformation;
import Definition.RoomClientMessage;
import Definition.ServerInformation;
import MainClass.TheApp;
import MyDataSets.PeerList;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by AUTOY on 2016/5/5.
 */
public class RoomClient {
    private final int RoomClientStart = 14000;
    private boolean onService = false;
    private DatagramSocket socket;
    private int port;
    private InetAddress address;
    private PeerList peerList;
    private RoomClientThread thread;
    private ServerInformation serverInformation;
    private ClientInformation clientInformation;
    private TheApp theApp;

    public RoomClient(TheApp theApp) {
        this.theApp = theApp;
        peerList = new PeerList();
        port = RoomClientStart;
        while (!trySocket(port)) {
            port++;
        }
        clientInformation = theApp.getMyClient();
        clientInformation.clientPort = port;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        thread = new RoomClientThread(this);
        thread.start();
        thread.suspend();
        onService = false;

    }

    private boolean trySocket(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    void receive(DatagramPacket dp) throws IOException {
        socket.receive(dp);
    }

    public boolean joinRoom(ServerInformation server, ClientInformation clientInformation) {
        this.serverInformation = server;
        this.clientInformation = clientInformation;
        try {
            String message = RoomClientMessage.JOIN + "|" + clientInformation.clientName + "|" + clientInformation.clientAddress.getHostAddress() + "|" + clientInformation.clientPort;
            Connection.sendDatagramMessage(message, socket, server.serverAddress, server.serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.resume();
        onService = true;
        return true;
    }

    public PeerList getPeerList() {
        return peerList;
    }

    public void sendChatMessage(String message) {
        try {
            Connection.sendDatagramMessage(RoomClientMessage.CHAT + "|" + clientInformation.type + "|" + message, socket, serverInformation.serverAddress, serverInformation.serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMoveMessage(String s) {
        try {
            String message = RoomClientMessage.MOVE + "|" + address.getHostAddress() + "|" + port + "|" + s;
            Connection.sendDatagramMessage(message, socket, serverInformation.serverAddress, serverInformation.serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updateList(String[] strings) {
        peerList.clear();
        int size = Integer.parseInt(strings[1]);
        int i, j;
        try {
            for (i = 0; i < size; i++) {
                j = 4 * i;
                peerList.add(strings[3 + j] + "|" + strings[4 + j], new ClientInformation(InetAddress.getByName(strings[3 + j]), strings[2 + j], Integer.parseInt(strings[4 + j]), strings[5 + j]));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        theApp.updatePlayerPanel(peerList);
        clientInformation.type = peerList.getValue(clientInformation.clientAddress.getHostAddress() + "|" + clientInformation.clientPort).type;
    }

    public boolean isOnService() {
        return onService;
    }

    public void leaveRoom() {
        peerList.clear();
        thread.suspend();
        onService = false;
    }

    void appendChatLn(String string, Color color) {
        theApp.appendChatLn(string, color);
    }

    void handlePlate(String string) {
        theApp.handlePlate(string);
    }

    void handleMove(String string) {
        theApp.handleMove(string);
    }

    void handleStart(String string) {
        theApp.handleStart(string);
    }

    void joinSucceeded() {
        theApp.joinSucceeded();
    }
}

