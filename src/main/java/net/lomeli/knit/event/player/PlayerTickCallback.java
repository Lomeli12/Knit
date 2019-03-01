package net.lomeli.knit.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerTickCallback {

    public static final Event<PlayerTickCallback> EVENT = EventFactory.createArrayBacked(PlayerTickCallback.class,
        (listeners) -> (player) -> {
            for (PlayerTickCallback callback : listeners) {
                callback.updateTick(player);
            }
        }
    );

    void updateTick(PlayerEntity player);
}
