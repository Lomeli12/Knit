package net.lomeli.knit.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.Screen;

@Environment(EnvType.CLIENT)
public interface PostScreenDrawCallback {

    public static final Event<PostScreenDrawCallback> EVENT = EventFactory.createArrayBacked(PostScreenDrawCallback.class,
        (listeners) -> (screen, mouseX, mouseY, lastFrameDuration) -> {
            for (PostScreenDrawCallback callback : listeners) {
                callback.postScreenDraw(screen, mouseX, mouseY, lastFrameDuration);
            }
        }
    );

    void postScreenDraw(Screen screen, int mouseX, int mouseY, float lastFrameDuration);
}
