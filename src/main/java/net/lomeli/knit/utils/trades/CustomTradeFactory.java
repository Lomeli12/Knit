package net.lomeli.knit.utils.trades;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TraderRecipe;
import net.minecraft.village.Trades;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class CustomTradeFactory implements Trades.Factory {
    private final ItemStack sellItem;
    private final CurrencyItem currencyOne;
    private final CurrencyItem currencyTwo;
    private final int maxUses;
    private final int xp;
    private final float priceMultiplier;

    /**
     * @param sellItem        Item being sold
     * @param currencyOne     First item the villager will buy. Cannot be null/Empty.
     * @param currencyTwo     Second item the villager will buy. Can be null/Empty;
     * @param maxUses         The amount of times the trade can be used before the trade is disabled.
     * @param xp              The amount of XP the trade gives the villager.
     * @param priceMultiplier Modifier that affects the price of the item.
     */
    public CustomTradeFactory(ItemStack sellItem, @Nonnull CurrencyItem currencyOne, @Nullable CurrencyItem currencyTwo,
                              int maxUses, int xp, float priceMultiplier) {
        this.sellItem = sellItem;
        this.currencyOne = currencyOne;
        this.currencyTwo = currencyTwo;
        this.maxUses = maxUses;
        this.xp = xp;
        this.priceMultiplier = priceMultiplier;
    }

    public CustomTradeFactory(ItemStack sellItem, CurrencyItem currencyItem, int maxUses, int xp, float priceMultiplier) {
        this(sellItem, currencyItem, null, maxUses, xp, priceMultiplier);
    }

    @Nullable
    @Override
    public TraderRecipe create(Entity trader, Random rand) {
        ItemStack stack0 = currencyOne.getCurrency(rand);
        ItemStack stack1 = currencyTwo != null ? currencyTwo.getCurrency(rand) : ItemStack.EMPTY;
        return new TraderRecipe(stack0, stack1, sellItem, maxUses, xp, priceMultiplier);
    }
}
