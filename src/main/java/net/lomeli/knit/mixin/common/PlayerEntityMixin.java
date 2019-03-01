package net.lomeli.knit.mixin.common;

import net.lomeli.knit.event.player.PlayerTickCallback;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "update", at = @At("TAIL"))
    private void playerUpdate(CallbackInfo callback) {
        PlayerTickCallback.EVENT.invoker().updateTick((PlayerEntity) (Object) this);
    }
}
