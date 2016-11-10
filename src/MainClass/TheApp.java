package MainClass;

import Definition.ClientInformation;
import Definition.ServerInformation;
import MyDataSets.PeerList;
import MyDataSets.PlateInformation;
import MyDataSets.ServerList;
import Network.PeerFinder;
import Network.RoomClient;
import Network.RoomServer;
import UI.EnterInfoDialog;
import UI.MainWindow;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by AUTOY on 2016/5/5.
 */
public class TheApp {
    MainWindow mainWindow;
    PeerFinder peerFinder;
    RoomClient roomClient;
    RoomServer roomServer;
    PlateInformation information;
    ServerList serverList = new ServerList();
    ClientInformation myClient;
    InetAddress address;

    TheApp(String string) {
        try {
            myClient = new ClientInformation(InetAddress.getLocalHost(), string, 0);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(0);
        }
        information = new PlateInformation(this);
        peerFinder = new PeerFinder(this);
        roomServer = new RoomServer(string, information);
        peerFinder.startAnswering(roomServer);
        roomClient = new RoomClient(this);
        mainWindow = new MainWindow(string, this, information);

        peerFinder.sendKnockMessage();
    }

    public static void main(String arg[]) {
        EnterInfoDialog dialog = new EnterInfoDialog();
        dialog.setLocationRelativeTo(null);
        dialog.setTitle("输入玩家昵称");
        dialog.show();
        if (dialog.isFlag()) {
            new TheApp(dialog.getTempString());
        } else {
            System.exit(0);
        }
    }

    /**
     * @return whether this the room is set up successfully
     */
    public boolean setUpRoom() {
        roomServer.startRoomServer();
        roomClient.joinRoom(roomServer.getServerInformation(), myClient);
        peerFinder.sendKnockMessage();
        return true;
    }


    public void searchServer() {
        serverList.clear();
        peerFinder.sendKnockMessage();
    }


    public void sendChatMessage(String message) {
        if (roomClient.isOnService()) {
            roomClient.sendChatMessage(message);
        }
    }

    public void sendMoveMessage(String message) {
        roomClient.sendMoveMessage(message);

    }

    public boolean joinGame(String s) {
        ServerInformation information = serverList.getServerInformation(s);
        roomClient.joinRoom(information, myClient);
        return true;
    }

    public void shutDown() {
        peerFinder.sendLeaveMessage();
        System.exit(0);
    }

    public void appendChatLn(String s, Color color) {
        mainWindow.appendChatLn(s, color);
    }

    public void handlePlate(String string) {
        mainWindow.handlePlate(string);
    }


    public void handleMove(String string) {
        mainWindow.handleMove(string);
    }

    public void leaveRoom() {
        roomClient.leaveRoom();
        roomServer.stopRunning();
        mainWindow.updatePlayerPanel(roomClient.getPeerList());
        peerFinder.sendLeaveMessage();
    }

    public void handleStart(String string) {
        mainWindow.handleStart(string);
        myClient.type = string;
    }

    public void updatePlayerPanel(PeerList peerList) {
        mainWindow.updatePlayerPanel(peerList);
    }

    public String getPlayerName() {
        return myClient.clientName;
    }

    public void joinSucceeded() {
        mainWindow.joinSucceeded();
    }

    public ClientInformation getMyClient() {
        return myClient;
    }

    public void handlePeerChange(String name, String address, String port) {
        try {
            serverList.add(address + "|" + port, new ServerInformation(InetAddress.getByName(address), name, Integer.parseInt(port)));
            mainWindow.updateRoomPanel(serverList.getServerMap());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void handlePeerLeave(String s) {
        serverList.remove(s);
        mainWindow.updateRoomPanel(serverList.getServerMap());
    }
}
