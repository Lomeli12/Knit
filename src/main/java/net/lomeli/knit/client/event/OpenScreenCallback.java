package net.lomeli.knit.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.Screen;

public interface OpenScreenCallback {

    public static final Event<OpenScreenCallback> EVENT = EventFactory.createArrayBacked(OpenScreenCallback.class,
        (listeners) -> (screen) -> {
            for (OpenScreenCallback callback : listeners) {
                callback.openingScreen(screen);
            }
        }
    );

    void openingScreen(Screen screen);
}
