package net.lomeli.knit.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerDeathCallback {

    public static final Event<PlayerDeathCallback> EVENT = EventFactory.createArrayBacked(PlayerDeathCallback.class,
        (listeners) -> (player, source) -> {
            for (PlayerDeathCallback callback: listeners) {
                callback.onPlayerDeath(player, source);
            }
        }
    );

    void onPlayerDeath(ServerPlayerEntity player, DamageSource source);
}
