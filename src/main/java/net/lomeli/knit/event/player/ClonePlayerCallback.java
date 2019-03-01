package net.lomeli.knit.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface ClonePlayerCallback {

    public static final Event<ClonePlayerCallback> EVENT = EventFactory.createArrayBacked(ClonePlayerCallback.class,
        (listeners) -> (player, oldPlayer, switchingDim) -> {
            for (ClonePlayerCallback callback : listeners) {
                callback.clonePlayer(player, oldPlayer, switchingDim);
            }
        }
    );

    void clonePlayer(ServerPlayerEntity player, ServerPlayerEntity oldPlayer, boolean switchingDim);
}
