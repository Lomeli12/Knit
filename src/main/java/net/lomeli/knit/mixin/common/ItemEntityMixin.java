package net.lomeli.knit.mixin.common;

import net.lomeli.knit.event.player.ItemPickupCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Inject(method = "onPlayerCollision", cancellable = true, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/item/ItemStack;getAmount()I"))
    private void onPlayerCollision(PlayerEntity player, CallbackInfo callback) {
        boolean pickup = ItemPickupCallback.EVENT.invoker().onItemPickup((ItemEntity) (Object) this, player);
        if (!pickup) {
            callback.cancel();
        }
    }
}
