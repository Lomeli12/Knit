package net.lomeli.knit.utils;

import com.google.common.collect.Maps;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CriteriaRegistry {
    private static final Map<Identifier, Criterion> CUSTOM_CRITERIA = Maps.newHashMap();

    public static <T extends Criterion> T registerCriterion(T criterion) {
        if (CUSTOM_CRITERIA.containsKey(criterion.getId()))
            throw new IllegalArgumentException("Duplicate custom criterion id " + criterion.getId());
        else {
            CUSTOM_CRITERIA.put(criterion.getId(), criterion);
            return criterion;
        }
    }

    public static Collection<Criterion> getAllCriteria() {
        return Collections.unmodifiableCollection(CUSTOM_CRITERIA.values());
    }

    public static Criterion getCriterionByID(Identifier id) {
        return CUSTOM_CRITERIA.get(id);
    }
}
