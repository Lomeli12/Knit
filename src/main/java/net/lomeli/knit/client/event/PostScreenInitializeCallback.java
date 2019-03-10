package net.lomeli.knit.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.Screen;

@Environment(EnvType.CLIENT)
public interface PostScreenInitializeCallback {

    public static final Event<PostScreenInitializeCallback> EVENT = EventFactory.createArrayBacked(PostScreenInitializeCallback.class,
        (listeners) -> (screen) -> {
            for (PostScreenInitializeCallback callback : listeners) {
                callback.postInitializeScreen(screen);
            }
        }
    );

    void postInitializeScreen(Screen screen);
}
