package net.lomeli.knit.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ClientDisconnectCallback {

    public static final Event<ClientDisconnectCallback> EVENT = EventFactory.createArrayBacked(ClientDisconnectCallback.class,
        (listeners) -> () -> {
            for (ClientDisconnectCallback callback : listeners) {
                callback.onClientDisconnect();
            }
        }
    );

    void onClientDisconnect();
}
