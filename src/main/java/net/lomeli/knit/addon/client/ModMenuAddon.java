package net.lomeli.knit.addon.client;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.Knit;
import net.lomeli.knit.KnitClient;
import net.lomeli.knit.client.screen.ModListScreen;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class ModMenuAddon {

    public static void loadModMenuAPI() {
        KnitClient.logger.info("Loading ModMenu addon.");
        ModMenuApi.addConfigOverride(Knit.MOD_ID, () -> {
            MinecraftClient.getInstance().openScreen(new ModListScreen(MinecraftClient.getInstance().currentScreen));
        });
    }
}
