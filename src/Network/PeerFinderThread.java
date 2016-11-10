package Network;

import Definition.PeerFinderMessage;

import java.io.IOException;
import java.net.DatagramPacket;

/**
 * Created by AUTOY on 2016/5/3.
 */
class PeerFinderThread extends Thread {
    boolean flag;
    private PeerFinder peerFinder;
    private RoomServer server;

    PeerFinderThread(PeerFinder peerFinder, RoomServer server) {
        this.peerFinder = peerFinder;
        this.server = server;
    }


    public void run() {
        flag = true;
        int length = 1024;
        byte[] buf = new byte[length];
        try {
            while (flag) {
                DatagramPacket dp = new DatagramPacket(buf, length);
                peerFinder.getSocket().receive(dp);
                String string = new String(dp.getData(), 0, dp.getLength(), "UTF8");
                parseCommand(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseCommand(String string) {
        String[] strings = string.split("\\|");
        System.out.println("PeerAnswer received:" + string);
        switch (strings[0]) {
            case PeerFinderMessage.KNOCK:
                try {
                    if (server.isRunning()) {
                        String CHANGE = PeerFinderMessage.CHANGE + '|' + server.getName() + '|' + peerFinder.getAddressString() + '|' + server.getPort();
                        peerFinder.sendMulticastMessage(CHANGE);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case PeerFinderMessage.CHANGE:
                peerFinder.handlePeerChange(strings[1], strings[2], strings[3]);
                break;
            case PeerFinderMessage.LEAVE:
                peerFinder.handlePeerLeave(strings[1], strings[2]);
                break;
        }
    }
}

