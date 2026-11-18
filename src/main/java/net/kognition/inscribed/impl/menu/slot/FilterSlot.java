package net.kognition.inscribed.impl.menu.slot;

import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.kognition.inscribed.impl.util.data.PearlType;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilterSlot extends Slot {
    private final RuneWeaverMenu menu;

    public FilterSlot(Container container, int slot, int x, int y, RuneWeaverMenu menu) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack itemStack) {
        if (super.mayPlace(itemStack) && menu.runeSlot.hasItem()) {
            for (PearlType type : menu.pearlGetter) {
                if (itemStack.is(type.targetTag())) return true;
            }
        }

        return false;
    }
}
