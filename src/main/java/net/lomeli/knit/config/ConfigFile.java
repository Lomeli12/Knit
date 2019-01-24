package net.lomeli.knit.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.impl.SyntaxError;
import com.google.common.base.Strings;
import net.fabricmc.loader.FabricLoader;
import net.lomeli.knit.Knit;
import net.lomeli.knit.utils.Logger;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;

// TODO: Replace once official Fabric config API is established
public class ConfigFile {
    private static final Logger CONFIG_LOG = new Logger(Knit.MOD_NAME + "/Config");
    private static final Jankson JANKSON = Jankson.builder().build();
    private static final File CONFIG_DIR = FabricLoader.INSTANCE.getConfigDirectory();

    private String modid;
    private Class<?> configClass;
    private boolean hasChanges;
    private File configFile;

    public ConfigFile(String modid, Class<?> configClass) {
        this.modid = modid;
        this.configFile = new File(CONFIG_DIR, modid + ".conf");
        this.configClass = configClass;
    }

    public void loadConfig() {
        CONFIG_LOG.info("Loading config for %s.", modid);
        if (!CONFIG_DIR.exists() || !configFile.exists() || !configFile.isFile()) {
            CONFIG_LOG.info("Creating config for %s.", modid);
            saveConfig();
        }

        try {
            JsonObject configJson = JANKSON.load(configFile);
            readJson(configJson);
        } catch (IOException ex) {
            CONFIG_LOG.exception("Failed to load config file %s.", ex, configFile.getName());
        } catch (SyntaxError ex) {
            CONFIG_LOG.exception("Syntax error in config file %s.", ex, configFile.getName());
        }

        if (hasChanges) {
            CONFIG_LOG.info("Writing changes to %s's config file.", modid);
            saveConfig();
        }
    }

    private void readJson(JsonObject json) {
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
            } catch (IllegalAccessException ex) {
                CONFIG_LOG.exception("Could not set config value!", ex);
            }

            field.setAccessible(access);
        }
    }

    @SuppressWarnings({"WeakerAccess", "ResultOfMethodCallIgnored"})
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
                CONFIG_LOG.exception("Failed to write config file %s", ex, configFile.getName());
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
            CONFIG_LOG.exception("Could not write config value for %s", ex, name);
        } catch (NullPointerException ex) {
            CONFIG_LOG.exception("", ex);
        }

        if (flag) {
            comment = null;
            if (!Strings.isNullOrEmpty(details.categoryComment()))
                comment = details.categoryComment();
            fileJson.put(category, parent, comment);
        }

        field.setAccessible(access);
    }
}
