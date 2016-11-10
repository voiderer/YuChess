package EventHandle;

import java.util.EventListener;

/**
 * Created by AUTOY on 2016/5/18.
 */
public interface PlayerListener extends EventListener {
    void chatSent(PlayerEvent pe);

    void moveSent(PlayerEvent pe);
}
