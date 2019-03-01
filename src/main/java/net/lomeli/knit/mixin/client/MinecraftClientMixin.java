package net.lomeli.knit.mixin.client;

import net.lomeli.knit.client.event.OpenScreenCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(method = "openScreen", at = @At(shift = At.Shift.AFTER, value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Screen;initialize(Lnet/minecraft/client/MinecraftClient;II)V"))
    private void openingScreen(Screen screen, CallbackInfo callback) {
        OpenScreenCallback.EVENT.invoker().openingScreen(screen);
    }
}