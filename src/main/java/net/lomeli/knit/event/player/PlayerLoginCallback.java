package net.lomeli.knit.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerLoginCallback {
    public static final Event<PlayerLoginCallback> EVENT = EventFactory.createArrayBacked(PlayerLoginCallback.class,
        (listeners) -> (connection, player) -> {
            for (PlayerLoginCallback callback : listeners) {
                callback.onPlayerLoggedIn(connection, player);
            }
        }
    );

    void onPlayerLoggedIn(ClientConnection connection, ServerPlayerEntity player);
}
