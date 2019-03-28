package net.lomeli.knit.client.screen.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.config.types.StringConfig;

@Environment(EnvType.CLIENT)
public class StringListEntry extends TextFieldListEntry<StringConfig> {

    public StringListEntry(ModConfigScreen parent, ConfigListWidget parentList, StringConfig config) {
        super(parent, parentList, config);
    }

    @Override
    public void save() {
        this.getConfigEntry().setConfigValue(this.textFieldWidget.getText());
    }
}
