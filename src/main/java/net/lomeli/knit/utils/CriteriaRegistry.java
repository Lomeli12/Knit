package net.lomeli.knit.utils;

import com.google.common.collect.Maps;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.Map;

public class CriteriaRegistry {
    private static final Map<Identifier, Criterion<?>> CUSTOM_CRITERIA = Maps.newHashMap();

    public static <T extends Criterion<?>> T registerCriterion(T criterion) {
        if (CUSTOM_CRITERIA.containsKey(criterion.getId()))
            throw new IllegalArgumentException("Duplicate custom criterion id " + criterion.getId());
        else {
            CUSTOM_CRITERIA.put(criterion.getId(), criterion);
            return criterion;
        }
    }

    public static Map<Identifier, Criterion<?>> getAllCriteria() {
        return Collections.unmodifiableMap(CUSTOM_CRITERIA);
    }
}
