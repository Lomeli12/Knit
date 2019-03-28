package net.lomeli.knit.client.screen;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.lomeli.knit.client.screen.entries.AbstractListEntry;
import net.lomeli.knit.client.screen.entries.ConfigListWidget;
import net.lomeli.knit.client.utils.ClientUtil;
import net.lomeli.knit.config.ConfigFile;
import net.lomeli.knit.config.types.ConfigField;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableTextComponent;

import java.util.List;

@Environment(EnvType.CLIENT)
public class ModConfigScreen extends Screen {
    private Screen parent;
    private ConfigFile config;
    private boolean hasChanges;
    private ConfigListWidget configListWidget;

    public ModConfigScreen(Screen parent, ModMetadata modMetadata, ConfigFile config) {
        super(new TranslatableTextComponent("screen.knit.mod.config", modMetadata.getName()));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        super.init();
        this.configListWidget = new ConfigListWidget(this, this.minecraft);
        this.children.add(this.configListWidget);
        this.setFocused(this.configListWidget);
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 25, 200, 20,
                I18n.translate("gui.done"), (buttonWidget) -> {
            if (this.hasChanges) {
                List<ConfigField> changes = Lists.newArrayList();
                for (AbstractListEntry entry : this.configListWidget.children()) {
                    entry.save();
                    changes.add(entry.getConfigEntry());
                }
                config.readFromArrays(changes);
            }
            this.minecraft.openScreen(parent);
        }));
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        this.renderBackground();
        this.setFocused(this.configListWidget);
        this.configListWidget.render(int_1, int_2, float_1);
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 15, 0xffffff);
        super.render(int_1, int_2, float_1);
        this.drawCenteredString(this.font, I18n.translate("screen.knit.mod.config.tooltip"),
                this.width / 2, this.height - 35, 0xffff00);

        if (this.configListWidget.getLastHoveredEntry() != null) {
            String comment = this.configListWidget.getLastHoveredEntry().getConfigEntry().getComment();
            if (!Strings.isNullOrEmpty(comment) && ClientUtil.safeKeyDown(ClientUtil.LEFT_SHIFT))
                this.renderTooltip(this.minecraft.textRenderer.wrapStringToWidthAsList(comment, 150), int_1, int_2);
        }
    }

    public void setHasChanges(boolean val) {
        this.hasChanges = val;
    }

    public ConfigFile getConfig() {
        return config;
    }
}
