package net.kognition.inscribed.impl.menu.slot;

import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.component.RuneComponent;
import net.kognition.inscribed.impl.index.ModDataComponents;
import net.kognition.inscribed.impl.index.ModItems;
import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.kognition.inscribed.impl.util.data.PearlPlacement;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class RuneSlot extends Slot {
    private static final Identifier EMPTY_SLOT_RUNE = Inscribed.id("container/slot/rune");
    private final RuneWeaverMenu menu;

    public RuneSlot(Container container, int slot, int x, int y, RuneWeaverMenu menu) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    public boolean mayPlace(ItemStack itemStack) {
        return super.mayPlace(itemStack) && itemStack.is(ModItems.RUNE);
    }

    public void onTake(Player player, ItemStack carried) {
        super.onTake(player, carried);
        if (menu.selected != null && menu.selected != PearlPlacement.BIG) {
            menu.pearlSlot.set(ItemStack.EMPTY);
            carried.set(ModDataComponents.RUNE, RuneComponent.Builder.create(menu));
        }
    }

    @Nullable
    public Identifier getNoItemIcon() {
        return EMPTY_SLOT_RUNE;
    }
}
