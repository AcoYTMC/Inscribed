package net.kognition.inscribed.impl.inventories.slot;

import net.kognition.inscribed.impl.inventories.RuneWeaverMenu;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilterSlot extends Slot {
    private final RuneWeaverMenu menu;

    public FilterSlot(Container container, int slot, int x, int y, RuneWeaverMenu menu) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack itemStack) {
        return super.mayPlace(itemStack) && itemStack.isDamageableItem() && menu.runeSlot.hasItem();
    }

    public void onTake(Player player, ItemStack carried) {
        super.onTake(player, carried);
        //menu.pearlSlot.setItem(0, ItemStack.EMPTY);
    }
}
