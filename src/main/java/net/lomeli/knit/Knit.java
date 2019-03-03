package net.lomeli.knit;

import net.fabricmc.api.ModInitializer;
import net.lomeli.knit.utils.Logger;

public class Knit implements ModInitializer {
    public static final String MOD_ID = "knit";
    public static final String MOD_NAME = "Knit";

    public static Logger log;

    @Override
    public void onInitialize() {
        log = new Logger(MOD_NAME);
    }
}
