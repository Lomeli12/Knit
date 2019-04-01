package net.lomeli.knit.client.screen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.lomeli.knit.config.ConfigFile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableTextComponent;

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
    protected void init() {
        super.init();
        this.configListWidget = new ConfigFileListWidget(this, this.minecraft);
        this.children.add(this.configListWidget);
        this.setFocused(this.configListWidget);
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 25, 200, 20,
                I18n.translate("gui.done"), (buttonWidget) -> this.minecraft.openScreen(this.parent)));
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        this.renderBackground();
        this.configListWidget.render(int_1, int_2, float_1);
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 15, 0xffffff);
        super.render(int_1, int_2, float_1);

    }

    private static class ConfigFileListWidget extends ElementListWidget<ConfigFileEntry> {
        private ConfigFileListWidget(ModMultiConfigScreen modMultiConfigScreen, MinecraftClient client) {
            super(client, modMultiConfigScreen.width, modMultiConfigScreen.height, 30,
                    modMultiConfigScreen.height - 40, 25);
            for (ConfigFile config : modMultiConfigScreen.configs)
                this.addItem(new ConfigFileEntry(modMultiConfigScreen, modMultiConfigScreen.modMetadata, config));
        }
    }

    private static class ConfigFileEntry extends ElementListWidget.ElementItem<ConfigFileEntry> implements ButtonWidget.PressAction {
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
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean mouseOver, float delta) {
            openConfigBtn.x = x + 10;
            openConfigBtn.y = y;
            openConfigBtn.render(mouseX, mouseY, delta);
        }

        @Override
        public List<? extends Element> children() {
            return ImmutableList.of(openConfigBtn);
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
