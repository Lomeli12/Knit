package net.lomeli.knit.mixin.client;

import net.lomeli.knit.client.event.OpenScreenCallback;
import net.lomeli.knit.client.event.PostScreenInitializeCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow
    public ClientWorld world;

    @Inject(method = "openScreen", at = @At("HEAD"), cancellable = true)
    private void openScreen(Screen screen, CallbackInfo callback) {
        boolean cancel = OpenScreenCallback.EVENT.invoker().openScreen(screen);
        if (cancel && this.world != null) {
            callback.cancel();
        }
    }

    @Inject(method = "openScreen", at = @At(shift = At.Shift.AFTER, value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Screen;init(Lnet/minecraft/client/MinecraftClient;II)V"))
    private void initializeScreen(Screen screen, CallbackInfo callback) {
        PostScreenInitializeCallback.EVENT.invoker().postInitializeScreen(screen);
    }
}