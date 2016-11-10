package Network;

import Definition.ClientInformation;
import Definition.ClientType;
import Definition.RoomServerMessage;
import Definition.ServerInformation;
import MyDataSets.PeerList;
import MyDataSets.PlateInformation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;

/**
 * Created by AUTOY on 2016/5/5.
 */
public class RoomServer {
    final int RoomServerStart = 12000;
    private boolean onService = false;
    private DatagramSocket socket;
    private int port;
    private RoomServerThread serverThread;
    private InetAddress address;
    private PeerList peerList;
    private ClientInformation clientRed;
    private ClientInformation clientBlue;
    private String name;
    private PlateInformation information;

    public RoomServer(String string, PlateInformation information) {
        this.information = information;
        name = string;
        peerList = new PeerList();
        port = RoomServerStart;
        serverThread = new RoomServerThread(this);
        serverThread.start();
        serverThread.suspend();
        while (!trySocket(port)) {
            port++;
        }
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private boolean trySocket(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public int getPort() {
        return port;
    }

    public void startRoomServer() {
        onService = true;
        serverThread.flag = true;
        serverThread.resume();
    }

    public void stopRunning() {
        if (onService) {
            peerList.clear();
            serverThread.flag = false;
            onService = false;
            serverThread.suspend();
        }
    }

    void sendChatMessage(String message) {
        Collection<ClientInformation> collection = peerList.getCollection();
        for (ClientInformation info : collection) {
            try {
                Connection.sendDatagramMessage(message, socket, info.clientAddress, info.clientPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void receive(DatagramPacket dp) throws IOException {
        socket.receive(dp);
    }

    void handleJoin(String name, String address, String port) {
        int size = peerList.getCount();
        ClientInformation client;
        try {
            client = new ClientInformation(InetAddress.getByName(address), name, Integer.parseInt(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        if (size == 0) {
            clientRed = client;
            clientRed.type = ClientType.RED;
        } else if (size == 1) {
            clientBlue = client;
            clientBlue.type = ClientType.BLUE;
        } else {
            client.type = ClientType.AUDIENCE;
        }
        peerList.add(address + "|" + port, client);
        try {
            Connection.sendDatagramMessage(RoomServerMessage.AKN_JOIN, socket, client.clientAddress, client.clientPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendPeerList();
        if (peerList.getCount() == 2) {
            startGame();
        } else if (peerList.getCount() > 2) {
            String message = RoomServerMessage.PLATE + "|" + information.saveToString();
            try {
                Connection.sendDatagramMessage(message, socket, client.clientAddress, client.clientPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPeerList() {
        String message = RoomServerMessage.LIST + "|" + peerList.getCount();
        Collection<ClientInformation> collection = peerList.getCollection();
        for (ClientInformation info : collection) {
            message += "|" + info.clientName + "|" + info.clientAddress.getHostAddress() + "|" + info.clientPort + "|" + info.type;
        }
        try {
            for (ClientInformation info : collection) {
                Connection.sendDatagramMessage(message, socket, info.clientAddress, info.clientPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String s, ClientInformation client) {
        try {
            Connection.sendDatagramMessage(s, socket, client.clientAddress, client.clientPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendStartMessage(ClientInformation client) {
        String message = RoomServerMessage.START + "|" + client.type;
        sendMessage(message, client);
    }

    private void sendOverMessage() {
        Collection<ClientInformation> collection = peerList.getCollection();
        collection.forEach((info) -> sendMessage(RoomServerMessage.OVER, info));
    }

    private void startGame() {
        Collection<ClientInformation> collection = peerList.getCollection();
        collection.forEach(this::sendStartMessage);
    }

    public ServerInformation getServerInformation() {
        return new ServerInformation(this.address, this.name, this.port);
    }

    void handleMove(String address, String port, String move) {
        ClientInformation information = peerList.getValue(address + "|" + port);
        Collection<ClientInformation> collection = peerList.getCollection();
        try {
            for (ClientInformation info : collection) {
                if (information != info) {
                    Connection.sendDatagramMessage(RoomServerMessage.MOVE + "|" + move, socket, info.clientAddress, info.clientPort);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isRunning() {
        return onService;
    }

    String getName() {
        return name;
    }
}


