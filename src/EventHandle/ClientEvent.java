package EventHandle;

import java.util.EventObject;

/**
 * Created by AUTOY on 2016/5/18.
 */
public class ClientEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ClientEvent(Object source) {
        super(source);
    }
}
