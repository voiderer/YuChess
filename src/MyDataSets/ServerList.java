package MyDataSets;

import Definition.ServerInformation;

import java.util.TreeMap;

/**
 * Created by AUTOY on 2016/5/7.
 */
public class ServerList {
    TreeMap<String, ServerInformation> serverMap = new TreeMap<>();

    public void add(String string, ServerInformation serverInformation) {
        serverMap.put(string, serverInformation);
    }

    public void clear() {
        serverMap.clear();
    }

    public TreeMap<String, ServerInformation> getServerMap() {
        return serverMap;
    }

    public ServerInformation getServerInformation(String s) {
        return serverMap.get(s);

    }

    public void remove(String s) {
        serverMap.remove(s);
    }
}
