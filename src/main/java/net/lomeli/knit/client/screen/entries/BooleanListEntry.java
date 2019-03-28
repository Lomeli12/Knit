package net.lomeli.knit.client.screen.entries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.config.types.BooleanConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;

@Environment(EnvType.CLIENT)
public class BooleanListEntry extends AbstractListEntry<BooleanConfig> implements ButtonWidget.class_4241 {
    private boolean value;
    private ButtonWidget buttonWidget;

    public BooleanListEntry(ModConfigScreen parentScreen, ConfigListWidget parentList, BooleanConfig config) {
        super(parentScreen, parentList, config);
        this.originalValue = config.getConfigValue();
        this.value = config.getConfigValue();
        this.buttonWidget = new ButtonWidget(0, 0, 150, 20, "", this);
    }

    @Override
    public void draw(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean mouseOver, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        this.buttonWidget.y = y;
        String btnText = String.valueOf(value);
        this.buttonWidget.setMessage(btnText.substring(0, 1).toUpperCase() + btnText.substring(1));
        String configName = getConfigEntry().getConfigName();
        if (client.textRenderer.isRightToLeft()) {
            client.textRenderer.drawWithShadow(configName, x + 175, y + 5, 16777215);
            this.buttonWidget.x = x + 14;
        } else {
            client.textRenderer.drawWithShadow(configName, x + 15, y + 5, 16777215);
            this.buttonWidget.x = x + 150;
        }
        buttonWidget.render(mouseX, mouseY, delta);
        super.draw(index, x, y, width, height, mouseX, mouseY, mouseOver, delta);
    }

    @Override
    public void save() {
        this.getConfigEntry().setConfigValue(this.value);
    }

    @Override
    public void onPress(ButtonWidget var1) {
        this.value = !this.value;
        if (this.value != (boolean) originalValue)
            this.getParentScreen().setHasChanges(true);
    }

    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        if (buttonWidget.mouseClicked(double_1, double_2, int_1))
            return true;
        return super.mouseClicked(double_1, double_2, int_1);
    }
}
