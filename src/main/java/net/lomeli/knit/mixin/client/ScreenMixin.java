package net.lomeli.knit.mixin.client;

import net.lomeli.knit.client.event.PostScreenDrawCallback;
import net.minecraft.client.gui.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Inject(method = "render(IIF)V", at = @At("RETURN"), remap = false)
    public void onPostDraw(int mouseX, int mouseY, float delta, CallbackInfo info) {
        PostScreenDrawCallback.EVENT.invoker().postScreenDraw((Screen) (Object) this, mouseX, mouseY, delta);
    }
}
