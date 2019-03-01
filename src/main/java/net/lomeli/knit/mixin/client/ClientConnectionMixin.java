package net.lomeli.knit.mixin.client;

import net.lomeli.knit.client.event.ClientDisconnectCallback;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    @Inject(method = "handleDisconnection", at = @At("TAIL"))
    private void playerDisconnect(CallbackInfo callback) {
        ClientDisconnectCallback.EVENT.invoker().onClientDisconnect();
    }
}
