package net.lomeli.knit.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;

import java.io.File;
import java.util.*;

public class ConfigManager {
    static final File CONFIG_DIR = FabricLoader.getInstance().getConfigDirectory();
    static final String CONFIG_EXT = "conf";

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

    public List<ConfigFile> getModConfigs(String modID) {
        Optional<ModContainer> optionalModContainer = FabricLoader.getInstance().getModContainer(modID);
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

    public File createUniqueConfigFile(String modID, boolean isClient) {
        String fileName = modID;
        if (isClient) fileName += "_client";
        fileName = ensureUniqueName(fileName, 0);
        return new File(CONFIG_DIR, String.format("%s.%s", fileName, CONFIG_EXT));
    }

    public String ensureUniqueName(String baseName, int count) {
        String name = String.format("%s_%s", baseName, count);
        for (List<ConfigFile> configList : MOD_CONFIGS.values()) {
            for (ConfigFile config : configList) {
                String fileName = config.getConfigFileName();
                if (fileName.equals(name))
                    return ensureUniqueName(baseName, count + 1);
            }
        }
        return name;
    }
}
