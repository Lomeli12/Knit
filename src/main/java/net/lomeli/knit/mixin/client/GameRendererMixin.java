package net.lomeli.knit.mixin.client;

import net.lomeli.knit.client.event.PostScreenDrawCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Screen;render(IIF)V",
            shift = At.Shift.AFTER))
    private void gameRender(float partialTicks, long time, boolean idk, CallbackInfo callback) {
        int mouseX = (int) (client.mouse.getX() * (double) client.window.getScaledWidth() / (double) client.window.getWidth());
        int mouseY = (int) (client.mouse.getY() * (double) client.window.getScaledHeight() / (double) client.window.getHeight());
        PostScreenDrawCallback.EVENT.invoker().postScreenDraw(client.currentScreen, mouseX, mouseY, client.getLastFrameDuration());
    }
}
