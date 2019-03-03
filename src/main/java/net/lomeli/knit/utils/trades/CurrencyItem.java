package net.lomeli.knit.utils.trades;

import net.minecraft.item.ItemProvider;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class CurrencyItem {
    private final ItemProvider item;
    private final int minAmount;
    private final int range;

    /**
     * Item that villager will buy.
     * @param provider Item that is being bought.
     * @param minAmount The minimum amount of that item required.
     * @param range The range of the amount. This is added onto the minimum amount.
     */
    public CurrencyItem(ItemProvider provider, int minAmount, int range) {
        this.item = provider;
        this.minAmount = minAmount;
        this.range = range;
    }

    public ItemStack getCurrency(Random random) {
        return new ItemStack(item, getAmount(random));
    }

    private int getAmount(Random random) {
        return minAmount + random.nextInt(range);
    }
}
