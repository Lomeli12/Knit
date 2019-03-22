package net.lomeli.knit.client.screen.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.config.types.IntConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class IntListEntry extends TextFieldListEntry<IntConfig> {

    private static Function<String, String> stripCharacter = str -> {
        StringBuilder builder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == '-')
                builder.append(c);
        }
        return builder.toString();
    };

    public IntListEntry(ModConfigScreen parent, ConfigListWidget parentList, IntConfig config) {
        super(parent, parentList, config);
        this.originalValue = config.getConfigValue();
        this.textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 148, 18) {
            @Override
            public void addText(String text) {
                super.addText(stripCharacter.apply(text));
            }
        };
        textFieldWidget.setText(config.getConfigValue().toString());
        textFieldWidget.setMaxLength(999999);
        textFieldWidget.setChangedListener(s -> {
            if (!originalValue.equals(s))
                parent.setHasChanges(true);
        });
    }

    private int getTextValue() {
        try {
            return Integer.valueOf(textFieldWidget.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void save() {
        this.getConfigEntry().setConfigValue(this.getTextValue());
    }
}
