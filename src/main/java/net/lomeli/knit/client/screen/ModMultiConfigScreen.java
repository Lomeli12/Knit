package net.lomeli.knit.client.screen;

import com.google.common.base.Strings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.lomeli.knit.client.utils.ClientUtil;
import net.lomeli.knit.config.ConfigFile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableTextComponent;

import java.awt.*;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ModMultiConfigScreen extends Screen {
    private List<ConfigFile> configs;
    private final Screen parent;
    private final ModMetadata modMetadata;

    private ConfigFileListWidget configListWidget;

    ModMultiConfigScreen(Screen parent, ModMetadata modMetadata, List<ConfigFile> configs) {
        super(new TranslatableTextComponent("screen.knit.mod.config.multi", modMetadata.getName()));
        this.parent = parent;
        this.modMetadata = modMetadata;
        this.configs = configs;
    }

    @Override
    protected void onInitialized() {
        super.onInitialized();
        this.configListWidget = new ConfigFileListWidget(this, this.client);
        this.listeners.add(this.configListWidget);
        this.focusOn(this.configListWidget);
        this.addButton(new ButtonWidget(this.screenWidth / 2 - 100, this.screenHeight - 25, 200, 20,
                I18n.translate("gui.done"), (buttonWidget) -> this.client.openScreen(this.parent)));
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        this.drawBackground();
        this.configListWidget.render(int_1, int_2, float_1);
        this.drawStringCentered(this.fontRenderer, this.title.getFormattedText(), this.screenWidth / 2, 15, 0xffffff);
        super.render(int_1, int_2, float_1);

    }

    private static class ConfigFileListWidget extends EntryListWidget<ConfigFileEntry> {
        private ConfigFileListWidget(ModMultiConfigScreen modMultiConfigScreen, MinecraftClient client) {
            super(client, modMultiConfigScreen.screenWidth, modMultiConfigScreen.screenHeight , 30,
                    modMultiConfigScreen.screenHeight - 40, 25);
            for (ConfigFile config : modMultiConfigScreen.configs)
                this.addEntry(new ConfigFileEntry(modMultiConfigScreen, modMultiConfigScreen.modMetadata, config));
        }
    }

    private static class ConfigFileEntry extends EntryListWidget.Entry<ConfigFileEntry> implements ButtonWidget.class_4241 {
        private ButtonWidget openConfigBtn;
        private final ModMetadata modMetadata;
        private final ConfigFile configFile;
        private final ModMultiConfigScreen parentScreen;

        private ConfigFileEntry(ModMultiConfigScreen parentScreen, ModMetadata modMetadata, ConfigFile configFile) {
            this.modMetadata = modMetadata;
            this.configFile = configFile;
            this.parentScreen = parentScreen;
            String name = I18n.translate(configFile.getConfigName());
            if (Strings.isNullOrEmpty(name))
                name = configFile.getConfigFileName();
            this.openConfigBtn = new ButtonWidget(0, 0, 200, 20, name, this);
        }

        @Override
        public void draw(int var1, int var2, int var3, int var4, boolean isSelected, float delta) {
            Point mouse = ClientUtil.getMousePoint();
            openConfigBtn.x = this.getX() + 10;
            openConfigBtn.y = this.getY();
            openConfigBtn.render(mouse.x, mouse.y, delta);
        }

        @Override
        public void onPress(ButtonWidget var1) {
            MinecraftClient.getInstance().openScreen(new ModConfigScreen(parentScreen, modMetadata, configFile));
        }

        @Override
        public boolean mouseClicked(double double_1, double double_2, int int_1) {
            if (openConfigBtn.mouseClicked(double_1, double_2, int_1))
                return true;
            return super.mouseClicked(double_1, double_2, int_1);
        }
    }
}
