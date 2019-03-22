package net.lomeli.knit.addon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.lomeli.knit.addon.client.ModMenuAddon;

@Environment(EnvType.CLIENT)
public class ClientAddonLoader {
    public static void loadAddons() {
        if (FabricLoader.getInstance().isModLoaded("modmenu"))
            ModMenuAddon.loadModMenuAPI();
    }
}
