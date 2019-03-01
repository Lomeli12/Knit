package net.lomeli.knit.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancement.SimpleAdvancement;
import net.minecraft.server.network.ServerPlayerEntity;

//TODO: Fabric PR?
public interface AdvancementCallback {
    public static final Event<AdvancementCallback> EVENT = EventFactory.createArrayBacked(AdvancementCallback.class,
        (listeners) -> (player, advancement) -> {
            for (AdvancementCallback event : listeners) {
                event.unlockedAdvancement(player, advancement);
            }
        }
    );

    void unlockedAdvancement(ServerPlayerEntity player, SimpleAdvancement advancement);
}
