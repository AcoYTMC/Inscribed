package net.kognition.inscribed.api.item;

import net.minecraft.world.item.ItemStack;

/**
 * @author AcoYT
 */
public interface ExtraBarItem {
    default boolean isExtraBarVisible(ItemStack stack) { return false; }
    default int getExtraBarColor(ItemStack stack) { return 0; }
    default int getExtraBarWidth(ItemStack stack) { return 0; }
}
