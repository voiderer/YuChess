package MyDataSets;

import Definition.ClientInformation;

import java.util.Collection;
import java.util.TreeMap;

/**
 * Created by AUTOY on 2016/5/7.
 */
public class PeerList {
    private TreeMap<String, ClientInformation> peerMap = new TreeMap<>();

    public void add(String string, ClientInformation serverInformation) {
        peerMap.put(string, serverInformation);
    }

    public void clear() {
        peerMap.clear();
    }

    public TreeMap<String, ClientInformation> getPeerMap() {
        return peerMap;
    }

    public int getCount() {
        return peerMap.size();
    }

    public Collection<ClientInformation> getCollection() {
        return peerMap.values();
    }

    public ClientInformation getValue(String s) {
        return peerMap.get(s);
    }
}
