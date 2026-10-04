package net.kognition.inscribed.impl.inventories.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class PearlDisplaySlot extends Slot {
    public PearlDisplaySlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }

    public boolean mayPickup(Player player) {
        return false;
    }

    public boolean isHighlightable() {
        return false;
    }
}
