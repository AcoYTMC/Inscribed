package net.kognition.inscribed.impl.block.entity;

import net.kognition.inscribed.impl.index.ModBlocks;
import net.kognition.inscribed.impl.index.ModItems;
import net.kognition.inscribed.impl.index.ModMenuTypes;
import net.kognition.inscribed.impl.util.ModUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author AcoYT
 */
public class RuneWeaverMenu extends AbstractContainerMenu {
    private final Container pearlSlot = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            RuneWeaverMenu.this.slotsChanged(this);
        }
    };

    private final Container runeSlot = new SimpleContainer(1);
    private final Container filterSlot = new SimpleContainer(1);

    public final Container displaySlots = new SimpleContainer(4);

    private final ContainerLevelAccess access;

    public RuneWeaverMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL, inventory.player.level());
    }

    public RuneWeaverMenu(int containerId, Inventory inventory, ContainerLevelAccess access, Level level) {
        super(ModMenuTypes.RUNE_WEAVER, containerId);
        this.access = access;

        this.addSlot(new PearlInsertSlot(pearlSlot, 0, 8, 34));
        this.addSlot(new RuneSlot(runeSlot, 0, 26, 34));
        this.addSlot(new FilterSlot(filterSlot, 0, 44, 34));

        this.addSlot(new PearlDisplaySlot(displaySlots, 0, ModUtil.TOP[0], ModUtil.TOP[1])); // Top
        this.addSlot(new PearlDisplaySlot(displaySlots, 0, ModUtil.LEFT[0], ModUtil.LEFT[1])); // Left
        this.addSlot(new PearlDisplaySlot(displaySlots, 0, ModUtil.BOTTOM[0], ModUtil.BOTTOM[1])); // Bottom
        this.addSlot(new PearlDisplaySlot(displaySlots, 0, ModUtil.RIGHT[0], ModUtil.RIGHT[1])); // Right

        this.addStandardInventorySlots(inventory, 8, 84);
    }

    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == filterSlot) {
            //
        }
    }

    public void removed(Player player) {
        super.removed(player);
        this.access.execute((_, _) -> {
            this.clearContainer(player, this.pearlSlot);
            this.clearContainer(player, this.runeSlot);
            this.clearContainer(player, this.filterSlot);
        });
    }

    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.RUNE_WEAVER.get());
    }

    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ItemStack.EMPTY;
    }

    public static class PearlInsertSlot extends Slot {
        public PearlInsertSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        public boolean mayPlace(ItemStack itemStack) {
            return itemStack.is(ModItems.PEARL);
        }
    }

    public static class RuneSlot extends Slot {
        public RuneSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        public boolean mayPlace(ItemStack itemStack) {
            return itemStack.is(ModItems.RUNE);
        }
    }

    public class FilterSlot extends Slot {
        public FilterSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        public boolean mayPlace(ItemStack itemStack) {
            return itemStack.isDamageableItem();
        }

        public void onTake(Player player, ItemStack carried) {
            super.onTake(player, carried);
            RuneWeaverMenu.this.pearlSlot.setItem(0, ItemStack.EMPTY);
        }
    }

    public static class PearlDisplaySlot extends Slot {
        public PearlDisplaySlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        public boolean mayPlace(ItemStack itemStack) {
            return false;
        }

        public boolean mayPickup(Player player) {
            return false;
        }
    }
}
