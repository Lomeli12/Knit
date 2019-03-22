package net.lomeli.knit.client.screen;

import com.google.common.base.Strings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.lomeli.knit.client.utils.ClientUtil;
import net.lomeli.knit.config.ConfigFile;
import net.lomeli.knit.config.ConfigManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableTextComponent;

import java.awt.*;
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
    protected void onInitialized() {
        super.onInitialized();
        this.modListWidget = new ModListWidget(this, this.client);
        this.listeners.add(this.modListWidget);
        this.focusOn(this.modListWidget);
        this.addButton(new ButtonWidget(this.screenWidth / 2 - 100, this.screenHeight - 25, 200, 20,
                I18n.translate("gui.done"), (buttonWidget) -> this.client.openScreen(this.parent)));
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        this.drawBackground();
        this.modListWidget.render(int_1, int_2, float_1);
        this.drawStringCentered(this.fontRenderer, this.title.getFormattedText(), this.screenWidth / 2, 15, 0xffffff);
        super.render(int_1, int_2, float_1);

    }

    private static class ModListWidget extends EntryListWidget<ModEntry> {
        private ModListWidget(ModListScreen modListscreen, MinecraftClient client) {
            super(client, modListscreen.screenWidth , modListscreen.screenHeight, 30,
                    modListscreen.screenHeight - 40, 25);
            for (ModMetadata metadata : modListscreen.mods) {
                this.addEntry(new ModEntry(modListscreen, metadata));
            }
        }
    }

    private static class ModEntry extends EntryListWidget.Entry<ModEntry> implements ButtonWidget.class_4241 {
        private ButtonWidget openConfigBtn;
        private final ModMetadata modMetadata;
        private final ModListScreen parentScreen;

        private ModEntry(ModListScreen parentScreen, ModMetadata modMetadata) {
            this.modMetadata = modMetadata;
            this.parentScreen = parentScreen;
            this.openConfigBtn = new ButtonWidget(0, 0,
                    200, 20, Strings.isNullOrEmpty(modMetadata.getName()) ? modMetadata.getId() : modMetadata.getName(), this);
        }

        @Override
        public void draw(int var1, int var2, int var3, int var4, boolean isSelected, float delta) {
            Point mouse = ClientUtil.getMousePoint();
            openConfigBtn.x = this.getX() + 10;
            openConfigBtn.y = this.getY();
            openConfigBtn.render(mouse.x, mouse.y, delta);
        }

        @Override
        public boolean mouseClicked(double double_1, double double_2, int int_1) {
            if (openConfigBtn.mouseClicked(double_1, double_2, int_1))
                return true;
            return super.mouseClicked(double_1, double_2, int_1);
        }

        @Override
        public void onPress(ButtonWidget var1) {
            List<ConfigFile> configs = ConfigManager.getInstance().getModConfigs(modMetadata.getId());
            if (configs.size() == 1)
                MinecraftClient.getInstance().openScreen(new ModConfigScreen(parentScreen, modMetadata, configs.get(0)));
            else if (configs.size() > 1)
                MinecraftClient.getInstance().openScreen(new ModMultiConfigScreen(parentScreen, modMetadata, configs));
        }
    }
}
