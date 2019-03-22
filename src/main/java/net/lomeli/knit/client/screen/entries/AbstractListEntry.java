package net.lomeli.knit.client.screen.entries;

import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.client.utils.ClientUtil;
import net.lomeli.knit.config.types.ConfigField;
import net.minecraft.client.gui.widget.EntryListWidget;

import java.awt.*;

public abstract class AbstractListEntry<T extends ConfigField> extends EntryListWidget.Entry<AbstractListEntry<?>> {
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
    public void draw(int var1, int var2, int var3, int var4, boolean var5, float var6) {
        Point point = ClientUtil.getMousePoint();
        if (this.isMouseOver(point.x, point.y))
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
