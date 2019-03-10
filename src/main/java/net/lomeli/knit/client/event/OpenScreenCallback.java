package net.lomeli.knit.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.gui.Screen;

import javax.annotation.Nullable;

@Environment(EnvType.CLIENT)
public interface OpenScreenCallback {

    public static final Event<OpenScreenCallback> EVENT = EventFactory.createArrayBacked(OpenScreenCallback.class,
         (listeners) -> (screen) -> {
             for (OpenScreenCallback callback : listeners) {
                 boolean flag = callback.openScreen(screen);
                 if (flag) {
                     return true;
                 }
             }
             return false;
         }
    );

    /**
     * @param screen Screen about to open. Can be null.
     * @return true to cancel screen opening.
     */
    boolean openScreen(@Nullable Screen screen);
}
