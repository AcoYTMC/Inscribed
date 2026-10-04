package net.kognition.inscribed.impl.inventories.slot;

import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class RuneSlot extends Slot {
    private static final Identifier EMPTY_SLOT_RUNE = Inscribed.id("container/slot/rune");

    public RuneSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public boolean mayPlace(ItemStack itemStack) {
        return super.mayPlace(itemStack) && itemStack.is(ModItems.RUNE);
    }

    @Nullable
    public Identifier getNoItemIcon() {
        return EMPTY_SLOT_RUNE;
    }
}
