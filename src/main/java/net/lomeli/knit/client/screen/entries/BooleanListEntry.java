package net.lomeli.knit.client.screen.entries;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.lomeli.knit.client.screen.ModConfigScreen;
import net.lomeli.knit.config.types.BooleanConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.util.List;

@Environment(EnvType.CLIENT)
public class BooleanListEntry extends AbstractListEntry<BooleanConfig> implements ButtonWidget.PressAction {
    private boolean value;
    private ButtonWidget valueToggle;

    public BooleanListEntry(ModConfigScreen parentScreen, ConfigListWidget parentList, BooleanConfig config) {
        super(parentScreen, parentList, config);
        this.originalValue = config.getConfigValue();
        this.value = config.getConfigValue();
        this.valueToggle = new ButtonWidget(0, 0, 150, 20, "", this);
    }

    @Override
    public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean mouseOver, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        this.valueToggle.y = y;
        String btnText = String.valueOf(value);
        this.valueToggle.setMessage(btnText.substring(0, 1).toUpperCase() + btnText.substring(1));
        String configName = getConfigEntry().getConfigName();
        if (client.textRenderer.isRightToLeft()) {
            client.textRenderer.drawWithShadow(configName, x + 175, y + 5, 16777215);
            this.valueToggle.x = x + 14;
        } else {
            client.textRenderer.drawWithShadow(configName, x + 15, y + 5, 16777215);
            this.valueToggle.x = x + 150;
        }
        valueToggle.render(mouseX, mouseY, delta);
        super.render(index, x, y, width, height, mouseX, mouseY, mouseOver, delta);
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
    public List<? extends Element> children() {
        return ImmutableList.of(valueToggle);
    }
}
