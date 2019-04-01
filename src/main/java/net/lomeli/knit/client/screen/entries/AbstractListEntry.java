package net.lomeli.knit.client.screen.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.config.types.ConfigField;
import net.minecraft.client.gui.widget.ElementListWidget;

@Environment(EnvType.CLIENT)
public abstract class AbstractListEntry<T extends ConfigField> extends ElementListWidget.ElementItem<AbstractListEntry<?>> {
    private ConfigListWidget parentList;
    private T configEntry;
    protected Object originalValue;
    private ModConfigScreen parent;

    public AbstractListEntry(ModConfigScreen parent, ConfigListWidget parentList, T configEntry) {
        this.parent = parent;
        this.parentList = parentList;
        this.configEntry = configEntry;
        this.originalValue = configEntry.getConfigValue();
    }

    @Override
    public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean mouseOver, float delta) {
        if (this.isMouseOver(mouseX, mouseY))
            parentList.setLastHoveredEntry(this);
        else if (parentList.getLastHoveredEntry() == this)
            parentList.setLastHoveredEntry(null);
    }

    public T getConfigEntry() {
        return configEntry;
    }

    public ModConfigScreen getParentScreen() {
        return parent;
    }

    public abstract void save();
}
