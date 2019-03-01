package net.lomeli.knit.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;

public interface PlayerRespawnCallback {

    public static final Event<PlayerRespawnCallback> EVENT = EventFactory.createArrayBacked(PlayerRespawnCallback.class,
        (listeners) -> (oldPlayer, newPlayer, dimType, keepEverything) -> {
            for (PlayerRespawnCallback callback : listeners) {
                callback.onPlayerRespawned(oldPlayer, newPlayer, dimType, keepEverything);
            }
        }
    );

    void onPlayerRespawned(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, DimensionType dimType, boolean keepEverything);
}
