package net.lomeli.knit.mixin.common;

import net.lomeli.knit.event.player.PlayerLoginCallback;
import net.lomeli.knit.event.player.PlayerRespawnCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void onPlayerLoggedIn(ClientConnection connection, ServerPlayerEntity player, CallbackInfo callback) {
        PlayerLoginCallback.EVENT.invoker().onPlayerLoggedIn(connection, player);
    }

    @Inject(method = "respawnPlayer", at = @At("TAIL"))
    private void onPlayerRespawned(ServerPlayerEntity player, DimensionType dimType, boolean keepEverything,
                                   CallbackInfoReturnable<ServerPlayerEntity> callback) {
        PlayerRespawnCallback.EVENT.invoker().onPlayerRespawned(player, callback.getReturnValue(), dimType, keepEverything);
    }
}