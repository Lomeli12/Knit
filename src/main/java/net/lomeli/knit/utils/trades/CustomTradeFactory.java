package net.lomeli.knit.utils.trades;

import net.minecraft.item.ItemStack;
import net.minecraft.village.Trader;
import net.minecraft.village.TraderRecipe;
import net.minecraft.village.Trades;

import java.util.Random;

public class CustomTradeFactory implements Trades.Factory {
    private final ItemStack sellItem;
    private final CurrencyItem currencyOne;
    private final CurrencyItem currencyTwo;

    /**
     * @param sellItem    Item being sold.
     * @param currencyOne First item the villager will buy. Cannot be null.
     * @param currencyTwo Second item the villager will buy. Nullable.
     */
    public CustomTradeFactory(ItemStack sellItem, CurrencyItem currencyOne, CurrencyItem currencyTwo) {
        this.sellItem = sellItem;
        this.currencyOne = currencyOne;
        this.currencyTwo = currencyTwo;
    }

    public CustomTradeFactory(ItemStack sellItem, CurrencyItem currencyItem) {
        this(sellItem, currencyItem, null);
    }

    @Override
    public TraderRecipe create(Trader trader, Random rand) {
        ItemStack stack0 = currencyOne.getCurrency(rand);
        ItemStack stack1 = currencyTwo != null ? currencyTwo.getCurrency(rand) : ItemStack.EMPTY;
        return new TraderRecipe(stack0, stack1, sellItem);
    }
}
