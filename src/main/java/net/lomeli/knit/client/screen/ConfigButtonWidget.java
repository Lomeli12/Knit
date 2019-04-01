package net.lomeli.knit.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.lomeli.knit.Knit;
import net.lomeli.knit.KnitClient;
import net.lomeli.knit.config.ConfigFile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Validate;

import java.io.InputStream;

class ConfigButtonWidget extends ButtonWidget {
    private static final MinecraftClient MC = MinecraftClient.getInstance();
    private Identifier modIconLocation;
    private NativeImageBackedTexture modIcon;

    ConfigButtonWidget(int x, int y, int width, int height, String text, ButtonWidget.PressAction pressAction,
                       ModMetadata modMetadata) {
        super(x, y, width, height, text, pressAction);
        if (modMetadata != null) {
            this.modIconLocation = new Identifier(Knit.MOD_ID, modMetadata.getId() + "_icon");
            this.modIcon = getNativeModIcon(modMetadata);
        }
    }

    ConfigButtonWidget(int x, int y, int width, int height, String text, ButtonWidget.PressAction pressAction) {
        this(x, y, width, height, text, pressAction, null);
    }

    void renderIcon(ConfigFile config, int x, int y) {
        GlStateManager.pushMatrix();
        GlStateManager.color4f(1f, 1f, 1f, 1f);
        if (modIcon != null) {
            MC.getTextureManager().bindTexture(modIconLocation);
            blit(x + 15, y + 2, 0f, 0f, 16, 16, 16f, 16f);
        } else if (config.getIconIdentifier() != null) {
            Sprite configIcon = MC.getSpriteAtlas().getSprite(config.getIconIdentifier());
            MC.getTextureManager().bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
            blit(x + 15, y + 2, configIcon, 16, 16);
        } else if (!config.getItemIcon().isEmpty()) {
            MC.getItemRenderer().renderGuiItem(config.getItemIcon(), x + 15, y + 2);
        }
        GlStateManager.popMatrix();
    }

    // Copied from ModMenu
    private NativeImageBackedTexture getNativeModIcon(ModMetadata modMetadata) {
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("assets/" + modMetadata.getId() + "/icon.png");
            Throwable streamError = null;
            NativeImageBackedTexture modIcon;
            try {
                NativeImage image = NativeImage.fromInputStream(stream);
                Validate.validState(image.getHeight() == image.getWidth(), "Must be square icon");
                NativeImageBackedTexture nativeImage = new NativeImageBackedTexture(image);
                MC.getTextureManager().registerTexture(this.modIconLocation, nativeImage);
                modIcon = nativeImage;
            } catch (Throwable thr) {
                streamError = thr;
                throw thr;
            } finally {
                if (stream != null) {
                    if (streamError != null) {
                        try {
                            stream.close();
                        } catch (Throwable thr) {
                            streamError.addSuppressed(thr);
                        }
                    } else {
                        stream.close();
                    }
                }
            }
            return modIcon;
        } catch (Throwable var16) {
            KnitClient.logger.error("Invalid icon for mod {}", modMetadata.getName(), var16);
            return null;
        }
    }
}
