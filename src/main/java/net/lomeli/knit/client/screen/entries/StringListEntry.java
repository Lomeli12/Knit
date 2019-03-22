package net.lomeli.knit.client.screen.entries;

import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.config.types.NumberConfig;
import net.lomeli.knit.config.types.StringConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.function.Function;

public class StringListEntry extends TextFieldListEntry<StringConfig> {

    public StringListEntry(ModConfigScreen parent, ConfigListWidget parentList, StringConfig config) {
        super(parent, parentList, config);
    }

    @Override
    public void save() {
        this.getConfigEntry().setConfigValue(this.textFieldWidget.getText());
    }
}
