package EventHandle;

import java.util.EventListener;

/**
 * Created by AUTOY on 2016/5/18.
 */
public interface CLientListener extends EventListener {
    void plateReceived(ClientEvent ce);

    void moveReceived(ClientEvent ce);

    void joinSucceed(ClientEvent ce);

    void chatReceieved(ClientEvent ce);

}
