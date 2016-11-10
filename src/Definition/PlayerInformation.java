package Definition;

/**
 * Created by AUTOY on 2016/5/3.
 */
public class PlayerInformation {
    public String name;
    public String address;
    public int port;

    PlayerInformation(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    PlayerInformation(PlayerInformation info) {
        this.name = info.name;
        this.address = info.address;
        this.port = info.port;
    }
}
