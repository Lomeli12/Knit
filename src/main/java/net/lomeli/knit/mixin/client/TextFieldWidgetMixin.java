package net.lomeli.knit.mixin.client;

import net.lomeli.knit.client.screen.entries.ITextFieldAccessor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin implements ITextFieldAccessor {
    @Shadow
    private int y;

    @Shadow
    @Final
    private int width;

    @Override
    public void setWidgetY(int y) {
        this.y = y;
    }

    @Override
    public int getWidgetWidth() {
        return this.width;
    }
}
