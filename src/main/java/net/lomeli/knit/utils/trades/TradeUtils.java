package net.lomeli.knit.utils.trades;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.village.Trades;
import net.minecraft.village.VillagerProfession;
import org.apache.commons.lang3.ArrayUtils;

public class TradeUtils {
    private static void editProfessionTrade(VillagerProfession profession, int level, boolean replaceLevel, Trades.Factory[] trades) {
        Int2ObjectMap<Trades.Factory[]> professionTrade = Trades.PROFESSION_TO_LEVELED_TRADE.get(profession);
        if (professionTrade.containsKey(level)) {
            Trades.Factory[] editedTrades = trades;
            if (!replaceLevel)
                editedTrades = ArrayUtils.addAll(professionTrade.get(level), trades);
            professionTrade.put(level, editedTrades);
        } else {
            // Min level is 1
            if (level < 1)
                level = 1;
            // Can't go from level 6 to level 8, etc
            if (level > professionTrade.size() + 1)
                level = professionTrade.size() + 1;

            professionTrade.put(level, trades);
        }
        Trades.PROFESSION_TO_LEVELED_TRADE.put(profession, professionTrade);
    }

    public static void addTradeToProfession(VillagerProfession profession, int level, Trades.Factory[] trades) {
        editProfessionTrade(profession, level, false, trades);
    }

    public static void replaceTradeForProfession(VillagerProfession profession, int level, Trades.Factory[] trades) {
        editProfessionTrade(profession, level, true, trades);
    }
}
