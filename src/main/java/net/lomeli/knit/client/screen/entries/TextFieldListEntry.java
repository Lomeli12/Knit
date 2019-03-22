package net.lomeli.knit.client.screen.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.client.utils.ClientUtil;
import net.lomeli.knit.config.types.ConfigField;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Window;

import java.awt.*;

@Environment(EnvType.CLIENT)
public abstract class TextFieldListEntry<T extends ConfigField> extends AbstractListEntry<T> {
    protected TextFieldWidget textFieldWidget;

    protected TextFieldListEntry(ModConfigScreen configScreen, ConfigListWidget parentList, T config) {
        super(configScreen, parentList, config);
        this.originalValue = config.getConfigValue();
        this.textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, 0, 0, 148, 18);
        textFieldWidget.setText(config.getConfigValue().toString());
        textFieldWidget.setMaxLength(999999);
        textFieldWidget.setChangedListener(s -> {
            if (!originalValue.equals(s))
                configScreen.setHasChanges(true);
        });
    }

    public abstract void save();

    @Override
    public void draw(int entryWidth, int height, int var3, int var4, boolean isSelected, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = client.window;
        Point mouse = ClientUtil.getMousePoint();
        ((ITextFieldAccessor) this.textFieldWidget).setWidgetY(this.getY());
        if (client.textRenderer.isRightToLeft()) {
            client.textRenderer.drawWithShadow(this.getConfigEntry().getConfigName(),
                    window.getScaledWidth() -
                            client.textRenderer.getStringWidth(this.getConfigEntry().getConfigName()) - getX() - 35,
                    getY() + 5, 16777215);
            this.textFieldWidget.setX(getX() + 36);
        } else {
            client.textRenderer.drawWithShadow(this.getConfigEntry().getConfigName(), getX() + 35, getY() + 5, 16777215);
            this.textFieldWidget.setX(window.getScaledWidth() - ((ITextFieldAccessor) this.textFieldWidget).getWidgetWidth() - getX() - 34);
        }
        this.textFieldWidget.render(mouse.x, mouse.y, delta);
        super.draw(entryWidth, height, var3, var4, isSelected, delta);
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
