package Network;

import Definition.ClientType;
import Definition.RoomServerMessage;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.UnknownHostException;

/**
 * Created by AUTOY on 2016/5/3.
 */
class RoomClientThread extends Thread {
    boolean flag;
    RoomClient roomClient;
    private int length = 1024;
    private byte[] buf = new byte[length];
    private DatagramPacket dp;

    RoomClientThread(RoomClient roomClient) {
        this.roomClient = roomClient;

    }

    public void run() {
        flag = true;
        try {
            while (flag) {
                dp = new DatagramPacket(buf, length);
                roomClient.receive(dp);
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
        System.out.println("roomClient received:" + string);
        switch (strings[0]) {
            case RoomServerMessage.CHAT:
                Color color;
                switch (strings[1]) {
                    case ClientType.BLUE:
                        color = Color.BLUE;
                        break;
                    case ClientType.RED:
                        color = Color.RED;
                        break;
                    default:
                        color = Color.GRAY;
                }
                roomClient.appendChatLn(strings[2], color);
                break;
            case RoomServerMessage.PLATE:
                roomClient.handlePlate(strings[1]);
                break;
            case RoomServerMessage.MOVE:
                roomClient.handleMove(strings[1]);
                break;
            case RoomServerMessage.START:
                roomClient.handleStart(strings[1]);
                break;
            case RoomServerMessage.LIST:
                roomClient.updateList(strings);
                break;
            case RoomServerMessage.AKN_JOIN:
                roomClient.joinSucceeded();
                break;
        }
    }
}
