package net.lomeli.knit.client.screen;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.lomeli.knit.config.ConfigFile;
import net.lomeli.knit.config.ConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableTextComponent;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ModListScreen extends Screen {
    private List<ModMetadata> mods;
    private final Screen parent;

    private ModListWidget modListWidget;

    public ModListScreen(Screen parent) {
        super(new TranslatableTextComponent("screen.knit.mod.config.edit"));
        this.parent = parent;
        this.mods = ConfigManager.getInstance().getModList();
    }

    @Override
    protected void init() {
        super.init();
        this.modListWidget = new ModListWidget(this, this.minecraft);
        this.children.add(this.modListWidget);
        this.setFocused(this.modListWidget);
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 25, 200, 20,
                I18n.translate("gui.done"), (buttonWidget) -> this.minecraft.openScreen(this.parent)));
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        this.renderBackground();
        this.modListWidget.render(int_1, int_2, float_1);
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 15, 0xffffff);
        super.render(int_1, int_2, float_1);

    }

    private static class ModListWidget extends ElementListWidget<ModEntry> {
        private ModListWidget(ModListScreen modListscreen, MinecraftClient client) {
            super(client, modListscreen.width, modListscreen.height, 30,
                    modListscreen.height - 40, 25);
            for (ModMetadata metadata : modListscreen.mods) {
                this.addItem(new ModEntry(modListscreen, metadata));
            }
        }
    }

    private static class ModEntry extends ElementListWidget.ElementItem<ModEntry> implements ButtonWidget.PressAction {
        private ConfigButtonWidget openConfigBtn;
        private final ModMetadata modMetadata;
        private final ModListScreen parentScreen;

        private ModEntry(ModListScreen parentScreen, ModMetadata modMetadata) {
            this.modMetadata = modMetadata;
            this.parentScreen = parentScreen;
            this.openConfigBtn = new ConfigButtonWidget(0, 0,
                    200, 20, Strings.isNullOrEmpty(modMetadata.getName()) ? modMetadata.getId() : modMetadata.getName(),
                    this, modMetadata);
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean mouseOver, float delta) {
            openConfigBtn.x = x + 10;
            openConfigBtn.y = y;
            openConfigBtn.render(mouseX, mouseY, delta);

            ConfigFile config = ConfigManager.getInstance().getModConfigs(modMetadata.getId()).get(0);
            openConfigBtn.renderIcon(config, x, y);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int int_1) {
            if (openConfigBtn.mouseClicked(mouseX, mouseY, int_1))
                return true;
            return super.mouseClicked(mouseX, mouseY, int_1);
        }

        @Override
        public void onPress(ButtonWidget buttonWidget) {
            openModConfigScreen(parentScreen, modMetadata);
        }

        @Override
        public List<? extends Element> children() {
            return ImmutableList.of(openConfigBtn);
        }
    }

    public static void openModConfigScreen(Screen parentScreen, ModMetadata modMetadata) {
        List<ConfigFile> configs = ConfigManager.getInstance().getModConfigs(modMetadata.getId());
        if (configs.size() == 1)
            MinecraftClient.getInstance().openScreen(new ModConfigScreen(parentScreen, modMetadata, configs.get(0)));
        else if (configs.size() > 1)
            MinecraftClient.getInstance().openScreen(new ModMultiConfigScreen(parentScreen, modMetadata, configs));
    }
}
