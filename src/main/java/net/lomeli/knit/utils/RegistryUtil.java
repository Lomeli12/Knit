package net.lomeli.knit.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegistryUtil {

    public static Item registerItem(Identifier id, Item item) {
        return Registry.register(Registry.ITEM, id, item);
    }

    public static Block registerBlock(Identifier id, Block block) {
        return Registry.register(Registry.BLOCK, id, block);
    }
}
