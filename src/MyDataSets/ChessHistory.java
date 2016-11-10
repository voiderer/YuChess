package MyDataSets;

import java.util.LinkedList;

/**
 * Created by AUTOY on 2016/5/5.
 */
public class ChessHistory {
    LinkedList<String> list = new LinkedList<>();

    public void add(String s) {
        list.addLast(s);
    }

    public String getLast() {
        return list.getLast();
    }

    public void revokeLast() {
        list.removeLast();
    }
}
