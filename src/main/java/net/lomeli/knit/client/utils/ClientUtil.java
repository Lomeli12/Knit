package net.lomeli.knit.client.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class ClientUtil {
    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    public static final int LEFT_SHIFT = 340;

    public static boolean safeKeyDown(int code) {
        try {
            return InputUtil.isKeyPressed(MinecraftClient.getInstance().window.getHandle(), code);
        } catch (Exception e) {
            // no op
        }
        return false;
    }
    
    public static Point getMousePoint() {
        return new Point((int) getMouseX(), (int) getMouseY());
    }
    
    public static double getMouseX() {
        return CLIENT.mouse.getX() * (double) CLIENT.window.getScaledWidth() / (double) CLIENT.window.getWidth();
    }
    
    public static double getMouseY() {
        return CLIENT.mouse.getY() * (double) CLIENT.window.getScaledWidth() / (double) CLIENT.window.getWidth();
    }
}
