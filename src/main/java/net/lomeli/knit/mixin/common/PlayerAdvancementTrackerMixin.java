package net.lomeli.knit.mixin.common;

import net.lomeli.knit.event.AdvancementCallback;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.SimpleAdvancement;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
    @Shadow
    private ServerPlayerEntity owner;

    @Inject(method = "grantCriterion", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementRewards;apply(Lnet/minecraft/server/network/ServerPlayerEntity;)V"))
    private void onAdvancement(SimpleAdvancement advancement, String criterion, CallbackInfoReturnable<Boolean> callback) {
        AdvancementCallback.EVENT.invoker().unlockedAdvancement(owner, advancement);
    }
}
