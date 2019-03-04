package net.lomeli.knit.mixin.common;

import net.lomeli.knit.utils.CriteriaRegistry;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Criterions.class)
public abstract class CriterionsMixin {

    @Shadow
    @Final
    private static Map<Identifier, Criterion<?>> VALUES;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void initClass(CallbackInfo callbackInfo) {
        VALUES.putAll(CriteriaRegistry.getAllCriteria());
    }
}
