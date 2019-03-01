package net.lomeli.knit.event.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface ItemPickupCallback {

    public static final Event<ItemPickupCallback> EVENT = EventFactory.createArrayBacked(ItemPickupCallback.class,
        (listeners) -> (itemEntity, player) -> {
            for (ItemPickupCallback callback : listeners) {
                boolean pickup = callback.onItemPickup(itemEntity, player);
                if (!pickup) {
                    return false;
                }
            }
            return true;
        }
    );

    boolean onItemPickup(ItemEntity itemEntity, PlayerEntity player);
}
