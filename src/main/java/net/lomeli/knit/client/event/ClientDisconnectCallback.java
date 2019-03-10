package net.lomeli.knit.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
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
