package net.lomeli.knit.mixin.common;

import com.google.common.collect.Lists;
import net.lomeli.knit.utils.CriteriaRegistry;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Criterions.class)
public abstract class CriterionsMixin {

    @Inject(method = "getById", cancellable = true, at = @At("TAIL"))
    private static void getCustomCriterionByID(Identifier id, CallbackInfoReturnable<Criterion> callback) {
        Criterion criterion = CriteriaRegistry.getCriterionByID(id);
        if (criterion != null) {
            callback.setReturnValue(criterion);
            callback.cancel();
        }
    }

    @Inject(method = "getAllCriterions", cancellable = true, at = @At("TAIL"))
    private static void getAllModCriterions(CallbackInfoReturnable<Iterable<Criterion>> callbackInfo) {
        Iterable<Criterion> it = callbackInfo.getReturnValue();
        List<Criterion> values = Lists.newArrayList();
        it.forEach(values::add);
        values.addAll(CriteriaRegistry.getAllCriteria());
        callbackInfo.setReturnValue(values);
        callbackInfo.cancel();
    }
}
