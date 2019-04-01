package net.lomeli.knit.client.screen.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.config.types.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ConfigListWidget extends ElementListWidget<AbstractListEntry<?>> {

    private AbstractListEntry<?> lastHoveredEntry;

    public ConfigListWidget(ModConfigScreen modConfigScreen, MinecraftClient client) {
        super(client, modConfigScreen.width, modConfigScreen.height, 30,
                modConfigScreen.height - 40, 25);
        for (Map.Entry<String, ConfigField> entry : modConfigScreen.getConfig().getConfigValues().entrySet()) {
            ConfigField field = entry.getValue();
            if (field instanceof IntConfig) {
                this.addItem(new IntListEntry(modConfigScreen, this, (IntConfig) field));
            } else if (field instanceof BooleanConfig) {
                this.addItem(new BooleanListEntry(modConfigScreen, this, (BooleanConfig) field));
            } else if (field instanceof NumberConfig) {
                this.addItem(new NumberListEntry(modConfigScreen, this, (NumberConfig) field));
            } else if (field instanceof StringConfig) {
                this.addItem(new StringListEntry(modConfigScreen, this, (StringConfig) field));
            }
        }
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 50;
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        super.render(int_1, int_2, float_1);
    }

    @Override
    public int getItemWidth() {
        return 300;
    }

    public AbstractListEntry<?> getLastHoveredEntry() {
        return lastHoveredEntry;
    }

    void setLastHoveredEntry(AbstractListEntry<?> lastHoveredEntry) {
        this.lastHoveredEntry = lastHoveredEntry;
    }
}
