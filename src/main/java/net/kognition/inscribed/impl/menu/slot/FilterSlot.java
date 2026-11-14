package net.kognition.inscribed.impl.menu.slot;

import net.kognition.inscribed.impl.Inscribed;
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

    // TODO: I have no idea how to tackle this with the way minecraft uses an interface so this'll have to do
    public boolean mayPlace(ItemStack itemStack) {
        if (super.mayPlace(itemStack) && menu.runeSlot.hasItem()) {
            final boolean[] isInTag = {false};
            menu.access.execute((level, _) -> {
                for (PearlType type : level.registryAccess().lookupOrThrow(Inscribed.PEARL_DATA_KEY)) {
                    if (itemStack.is(type.targetTag())) isInTag[0] = true;
                }
            });
            return isInTag[0];
        }

        return false;
    }
}
