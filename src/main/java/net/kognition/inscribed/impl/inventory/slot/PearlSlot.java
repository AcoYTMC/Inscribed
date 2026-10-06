package net.kognition.inscribed.impl.inventory.slot;

import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.ModItems;
import net.kognition.inscribed.impl.inventory.RuneWeaverMenu;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class PearlSlot extends Slot {
    private static final Identifier EMPTY_SLOT_PEARL = Inscribed.id("container/slot/pearl");
    private final RuneWeaverMenu menu;

    public PearlSlot(Container container, int slot, int x, int y, RuneWeaverMenu menu) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack itemStack) {
        return super.mayPlace(itemStack) && itemStack.is(ModItems.PEARL) && this.menu.runeSlot.hasItem();
    }

    @Nullable
    public Identifier getNoItemIcon() {
        return this.menu.runeSlot.hasItem() ? EMPTY_SLOT_PEARL : null;
    }
}
