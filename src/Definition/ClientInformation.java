package Definition;

import java.net.InetAddress;

/**
 * Created by AUTOY on 2016/5/7.
 */
public class ClientInformation {
    public InetAddress clientAddress;
    public String clientName;
    public int clientPort;
    public String type = ClientType.AUDIENCE;

    public ClientInformation(InetAddress clientAddress, String clientName, int clientPort) {
        this.clientAddress = clientAddress;
        this.clientName = clientName;
        this.clientPort = clientPort;
    }

    public ClientInformation(InetAddress clientAddress, String clientName, int clientPort, String type) {
        this.clientAddress = clientAddress;
        this.clientName = clientName;
        this.clientPort = clientPort;
        this.type = type;
    }
}
