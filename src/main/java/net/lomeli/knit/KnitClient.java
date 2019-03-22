package net.lomeli.knit;

import net.fabricmc.api.ClientModInitializer;
import net.lomeli.knit.addon.ClientAddonLoader;
import net.lomeli.knit.utils.Logger;

public class KnitClient implements ClientModInitializer {
    public static Logger logger;

    @Override
    public void onInitializeClient() {
        logger = new Logger(Knit.MOD_NAME + "/Client");

        logger.info("Loading client side addons");
        ClientAddonLoader.loadAddons();
    }
}
