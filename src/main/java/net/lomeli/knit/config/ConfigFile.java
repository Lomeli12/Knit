package net.lomeli.knit.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import net.fabricmc.loader.api.FabricLoader;
import net.lomeli.knit.Knit;
import net.lomeli.knit.config.types.*;
import net.lomeli.knit.utils.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

// TODO: Replace once official Fabric config API is established
public class ConfigFile {
    private static final Logger CONFIG_LOG = new Logger(Knit.MOD_NAME + "/Config");
    private static final Jankson JANKSON = Jankson.builder().build();
    private static final File CONFIG_DIR = FabricLoader.getInstance().getConfigDirectory();
    private static final String CONFIG_EXT = "conf";

    private String modID;
    private String configName;
    private Class<?> configClass;
    private boolean clentSide;
    private File configFile;
    private Map<String, ConfigField> configValues;

    public ConfigFile(String modID, String configName, Class<?> configClass, boolean clentSide) {
        this.modID = modID;
        this.configName = configName;
        this.configFile = new File(CONFIG_DIR, String.format("%s.%s", clentSide ? modID + "_client" : modID, CONFIG_EXT));
        this.configClass = configClass;
        this.clentSide = clentSide;
        this.configValues = Maps.newHashMap();
        ConfigManager.getInstance().registerModConfig(this);
    }

    public ConfigFile(String modID, Class<?> configClass, boolean clentSide) {
        this(modID, null, configClass, clentSide);
    }

    public ConfigFile(String modID, String configName, Class<?> configClass) {
        this(modID, configName, configClass, false);
    }

    public ConfigFile(String modID, Class<?> configClass) {
        this(modID, configClass, false);
    }

    public void setConfigFileName(String name) {
        this.configFile = new File(CONFIG_DIR,
                FilenameUtils.getBaseName(name).equalsIgnoreCase(CONFIG_EXT) ? name : name + ".conf");
    }

    public void loadConfig() {
        CONFIG_LOG.info("Loading config for {}.", modID);
        if (!CONFIG_DIR.exists() || !configFile.exists() || !configFile.isFile()) {
            CONFIG_LOG.info("Creating config for {}.", modID);
            saveConfig();
        }

        try {
            JsonObject configJson = JANKSON.load(configFile);
            readJson(configJson);
        } catch (IOException ex) {
            CONFIG_LOG.exception("Failed to load config file {}.", ex, configFile.getName());
        } catch (SyntaxError ex) {
            CONFIG_LOG.exception("Syntax error in config file {}.", ex, configFile.getName());
        }
    }

    public void readFromArrays(List<ConfigField> fieldList) {
        Field[] fields = configClass.getDeclaredFields();
        if (fields == null || fields.length < 1)
            return;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Config.class) || !Modifier.isStatic(field.getModifiers()))
                continue;
            boolean access = field.isAccessible();
            field.setAccessible(true);
            Config details = field.getAnnotation(Config.class);

            String name = Strings.isNullOrEmpty(details.configName()) ? field.getName() : details.configName();

            fieldMatch:
            for (ConfigField configField : fieldList) {
                if (name.equals(configField.getConfigName())) {
                    try {
                        Object value = configField.getConfigValue();
                        field.set(null, value);
                        if (!details.hideConfig())
                            registerFieldValue(name, details.comment(), field);
                    } catch (IllegalAccessException ex) {
                        CONFIG_LOG.exception("Could not set config value!", ex);
                    }
                    break fieldMatch;
                }
            }

            field.setAccessible(access);
        }
        saveConfig();
    }

    private void readJson(JsonObject json) {
        configValues.clear();
        Field[] fields = configClass.getDeclaredFields();
        if (fields == null || fields.length < 1)
            return;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Config.class) || !Modifier.isStatic(field.getModifiers()))
                continue;
            boolean access = field.isAccessible();
            field.setAccessible(true);
            Config details = field.getAnnotation(Config.class);

            JsonObject parent = json;
            if (!Strings.isNullOrEmpty(details.category())) {
                if (json.containsKey(details.category()))
                    parent = json.getObject(details.category());
                else
                    continue;

                if (parent == null)
                    continue;
            }

            String name = Strings.isNullOrEmpty(details.configName()) ? field.getName() : details.configName();

            try {
                Object value = parent.get(field.getType(), name);
                field.set(null, value);
                if (!details.hideConfig())
                    registerFieldValue(name, details.comment(), field);
            } catch (IllegalAccessException ex) {
                CONFIG_LOG.exception("Could not set config value!", ex);
            }

            field.setAccessible(access);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveConfig() {
        if (!CONFIG_DIR.exists())
            CONFIG_DIR.mkdir();

        JsonObject parent = new JsonObject();
        Field[] fields = configClass.getDeclaredFields();
        if (fields == null || fields.length < 1)
            return;
        for (Field field : fields) {
            saveField(parent, field);
        }

        if (parent.size() > 0) {
            String data = parent.toJson(true, true, 0);
            try {
                FileUtils.writeStringToFile(configFile, data, Charset.forName("UTF-8"));
            } catch (IOException ex) {
                CONFIG_LOG.exception("Failed to write config file {}", ex, configFile.getName());
            }
        }
    }

    private void saveField(JsonObject fileJson, Field field) {
        if (!field.isAnnotationPresent(Config.class) || !Modifier.isStatic(field.getModifiers()))
            return;
        boolean flag = false;
        boolean access = field.isAccessible();
        field.setAccessible(true);

        Config details = field.getAnnotation(Config.class);
        String name = Strings.isNullOrEmpty(details.configName()) ? field.getName() : details.configName();
        String comment = Strings.isNullOrEmpty(details.comment()) ? null : details.comment();
        String category = Strings.isNullOrEmpty(details.category()) ? null : details.category();

        JsonObject parent = fileJson;
        if (!Strings.isNullOrEmpty(category)) {
            flag = true;
            if (fileJson.containsKey(category))
                parent = fileJson.getObject(category);
            else
                parent = new JsonObject();
        }

        if (parent == null)
            parent = new JsonObject();

        try {
            parent.putDefault(name, field.get(null), comment);
        } catch (IllegalAccessException ex) {
            CONFIG_LOG.exception("Could not write config value for {}", ex, name);
        } catch (NullPointerException ex) {
            CONFIG_LOG.exception("", ex);
        }

        if (flag) {
            comment = null;
            if (!Strings.isNullOrEmpty(details.categoryComment()))
                comment = details.categoryComment();
            fileJson.put(category, parent, comment);
        }

        if (!details.hideConfig())
            registerFieldValue(name, comment, field);

        field.setAccessible(access);
    }

    private void registerFieldValue(String name, String comment, Field field) {
        try {
            Object value = field.get(null);
            ConfigField configField = null;

            if (value instanceof Integer)
                configField = new IntConfig(name, comment, (int) value);
            if (value instanceof Double)
                configField = new NumberConfig(name, comment, (double) value);
            if (value instanceof Boolean)
                configField = new BooleanConfig(name, comment, (boolean) value);
            if (value instanceof String)
                configField = new StringConfig(name, comment, (String) value);

            if (configField != null)
                configValues.put(name, configField);
        } catch (IllegalAccessException ex) {
            CONFIG_LOG.exception("Failed to register config field {}.", ex, name);
        }
    }

    public String getModID() {
        return modID;
    }

    public boolean isClentSide() {
        return clentSide;
    }

    public String getConfigFileName() {
        return FilenameUtils.getBaseName(this.configFile.getName());
    }

    public String getConfigName() {
        return configName;
    }

    public Map<String, ConfigField> getConfigValues() {
        return configValues;
    }
}
