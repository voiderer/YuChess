package Network;

import Definition.RoomClientMessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.UnknownHostException;

/**
 * Created by AUTOY on 2016/5/7.
 */
class RoomServerThread extends Thread {
    boolean flag;
    RoomServer roomServer;
    private int length = 1024;
    private byte[] buf = new byte[length];
    private DatagramPacket dp;

    RoomServerThread(RoomServer roomServer) {
        this.roomServer = roomServer;
    }

    public void run() {
        flag = true;
        try {
            while (flag) {
                dp = new DatagramPacket(buf, length);
                roomServer.receive(dp);
                String string = new String(dp.getData(), 0, dp.getLength(), "UTF8");
                parseCommand(string);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseCommand(String string) {
        String[] strings = string.split("\\|");
        System.out.println("roomServer received:" + string);
        switch (strings[0]) {
            case RoomClientMessage.CHAT:
                roomServer.sendChatMessage(string);
                break;
            case RoomClientMessage.JOIN:
                roomServer.handleJoin(strings[1], strings[2], strings[3]);
                break;
            case RoomClientMessage.MOVE:
                roomServer.handleMove(strings[1], strings[2], strings[3]);
                break;
            case RoomClientMessage.LEAVE:
                break;
            case RoomClientMessage.READY:
                break;
        }
    }
}
