package net.lomeli.knit.addon.client;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.KnitClient;
import net.lomeli.knit.client.screen.ModListScreen;
import net.lomeli.knit.config.ConfigManager;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class ModMenuAddon {
    public static void loadModMenuAPI() {
        KnitClient.logger.info("Loading ModMenu addon.");
        ConfigManager.getInstance().getModList().forEach(modMetadata ->
                ModMenuApi.addConfigOverride(modMetadata.getId(), () ->
                        ModListScreen.openModConfigScreen(MinecraftClient.getInstance().currentScreen, modMetadata)
                )
        );
    }
}
