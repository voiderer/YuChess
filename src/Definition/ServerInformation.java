package Definition;

import java.net.InetAddress;

/**
 * Created by AUTOY on 2016/5/7.
 */
public class ServerInformation {
    public InetAddress serverAddress;
    public String serverName;
    public int serverPort;

    public ServerInformation(InetAddress serverAddress, String serverName, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverName = serverName;
        this.serverPort = serverPort;
    }
}
