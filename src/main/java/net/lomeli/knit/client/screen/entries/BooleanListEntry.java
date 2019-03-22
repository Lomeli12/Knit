package net.lomeli.knit.client.screen.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.client.utils.ClientUtil;
import net.lomeli.knit.config.types.BooleanConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Window;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Environment(EnvType.CLIENT)
public class BooleanListEntry extends AbstractListEntry<BooleanConfig> implements ButtonWidget.class_4241 {
    private AtomicBoolean bool;
    private ButtonWidget buttonWidget;

    public BooleanListEntry(ModConfigScreen parentScreen, ConfigListWidget parentList, BooleanConfig config) {
        super(parentScreen, parentList, config);
        this.originalValue = config.getConfigValue();
        this.bool = new AtomicBoolean(config.getConfigValue());
        this.buttonWidget = new ButtonWidget(0, 0, 150, 20, "", this);
    }

    @Override
    public void draw(int entryWidth, int height, int var3, int var4, boolean isSelected, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = client.window;
        Point mouse = ClientUtil.getMousePoint();
        this.buttonWidget.y = getY();
        this.buttonWidget.setMessage(String.valueOf(bool.get()));
        if (client.textRenderer.isRightToLeft()) {
            client.textRenderer.drawWithShadow(getConfigEntry().getConfigName(),
                    window.getScaledWidth() -
                            client.textRenderer.getStringWidth(getConfigEntry().getConfigName()) - getX() - 35,
                    getY() + 5, 16777215);
            this.buttonWidget.x = getX() + 35;
        } else {
            client.textRenderer.drawWithShadow(getConfigEntry().getConfigName(), getX() + 35, getY() + 5, 16777215);
            this.buttonWidget.x = window.getScaledWidth() - buttonWidget.getWidth() - getX() - 35;
        }
        buttonWidget.render(mouse.x, mouse.y, delta);
        super.draw(entryWidth, height, var3, var4, isSelected, delta);
    }

    @Override
    public void save() {
        this.getConfigEntry().setConfigValue(this.bool.get());
    }

    @Override
    public void onPress(ButtonWidget var1) {
        this.bool.set(!this.bool.get());
        if (this.bool.get() != (boolean) originalValue)
            this.getParentScreen().setHasChanges(true);
    }

    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (buttonWidget.mouseClicked(double_1, double_2, int_1))
            return true;
        return super.mouseClicked(double_1, double_2, int_1);
    }
}
