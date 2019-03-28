package net.lomeli.knit.client.screen.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.config.types.ConfigField;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;

@Environment(EnvType.CLIENT)
public abstract class TextFieldListEntry<T extends ConfigField> extends AbstractListEntry<T> {
    protected TextFieldWidget textFieldWidget;

    protected TextFieldListEntry(ModConfigScreen configScreen, ConfigListWidget parentList, T config) {
        super(configScreen, parentList, config);
        this.originalValue = config.getConfigValue();
        this.textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 148, 18);
        textFieldWidget.setMaxLength(999999);
        textFieldWidget.setText(config.getConfigValue().toString());
        textFieldWidget.setChangedListener(s -> {
            if (!originalValue.equals(s))
                configScreen.setHasChanges(true);
        });
    }

    public abstract void save();

    @Override
    public void draw(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean mouseOver, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        ((ITextFieldAccessor) this.textFieldWidget).setWidgetY(y);
        String configName = getConfigEntry().getConfigName();
        if (client.textRenderer.isRightToLeft()) {
            client.textRenderer.drawWithShadow(configName, x + 175, y + 5, 16777215);
            this.textFieldWidget.setX(x + 15);
        } else {
            client.textRenderer.drawWithShadow(configName, x + 15, y + 5, 16777215);
            this.textFieldWidget.setX(x + 151);
        }
        this.textFieldWidget.render(mouseX, mouseY, delta);
        super.draw(index, x, y, width, height, mouseX, mouseY, mouseOver, delta);
    }

    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (textFieldWidget.mouseClicked(double_1, double_2, int_1))
            return true;
        return super.mouseClicked(double_1, double_2, int_1);
    }

    @Override
    public boolean keyPressed(int int_1, int int_2, int int_3) {
        if (textFieldWidget.keyPressed(int_1, int_2, int_3))
            return true;
        return super.keyPressed(int_1, int_2, int_3);
    }

    @Override
    public boolean charTyped(char char_1, int int_1) {
        if (textFieldWidget.charTyped(char_1, int_1))
            return true;
        return super.charTyped(char_1, int_1);
    }
}
