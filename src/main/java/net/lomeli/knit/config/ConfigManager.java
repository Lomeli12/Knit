package net.lomeli.knit.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.util.*;

public class ConfigManager {
    private static ConfigManager INSTANCE;

    private final Map<ModMetadata, List<ConfigFile>> MOD_CONFIGS;

    public static ConfigManager getInstance() {
        if (INSTANCE == null) INSTANCE = new ConfigManager();
        return INSTANCE;
    }

    private ConfigManager() {
        MOD_CONFIGS = Maps.newHashMap();
    }

    void registerModConfig(ConfigFile config) {
        Optional<ModContainer> optionalModContainer = FabricLoader.getInstance().getModContainer(config.getModID());
        if (optionalModContainer.isPresent()) {
            ModMetadata metadata = optionalModContainer.get().getMetadata();
            List<ConfigFile> configs = Lists.newArrayList();
            if (MOD_CONFIGS.containsKey(metadata))
                configs = MOD_CONFIGS.get(metadata);
            configs.add(config);
            MOD_CONFIGS.put(metadata, configs);
        }
    }

    public List<ModMetadata> getModList() {
        return Collections.unmodifiableList(new ArrayList<>(MOD_CONFIGS.keySet()));
    }

    public List<ConfigFile> getModConfigs(String modid) {
        Optional<ModContainer> optionalModContainer = FabricLoader.getInstance().getModContainer(modid);
        if (optionalModContainer.isPresent()) {
            ModMetadata metadata = optionalModContainer.get().getMetadata();
            if (MOD_CONFIGS.containsKey(metadata))
                return Collections.unmodifiableList(MOD_CONFIGS.get(metadata));
        }
        return Collections.EMPTY_LIST;
    }

    public Map<ModMetadata, List<ConfigFile>> getModConfigs() {
        return Collections.unmodifiableMap(MOD_CONFIGS);
    }
}
