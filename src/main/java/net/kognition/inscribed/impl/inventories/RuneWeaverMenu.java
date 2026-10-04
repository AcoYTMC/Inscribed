package net.kognition.inscribed.impl.inventories;

import net.kognition.inscribed.impl.block.entity.RuneWeaverBlockEntity;
import net.kognition.inscribed.impl.index.ModBlocks;
import net.kognition.inscribed.impl.index.ModMenuTypes;
import net.kognition.inscribed.impl.inventories.slot.FilterSlot;
import net.kognition.inscribed.impl.inventories.slot.PearlDisplaySlot;
import net.kognition.inscribed.impl.inventories.slot.PearlInsertSlot;
import net.kognition.inscribed.impl.inventories.slot.RuneSlot;
import net.kognition.inscribed.impl.util.ModUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * @author AcoYT
 */
public class RuneWeaverMenu extends AbstractContainerMenu {
    private final Container pearlInventory = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            RuneWeaverMenu.this.slotsChanged(this);
        }
    };

    public final Container runeInventory = new SimpleContainer(1) {
        public void setItem(int slot, ItemStack itemStack) {
            super.setItem(slot, itemStack);
            access.execute((level, pos) -> {
                if (level.getBlockEntity(pos) instanceof RuneWeaverBlockEntity weaverBlock) {
                    weaverBlock.setRuneStack(itemStack);
                }
            });
        }
    };

    public final Container filterInventory = new SimpleContainer(1);
    public final Container displaysInventory = new SimpleContainer(4);

    public final Slot pearlSlot;
    public final Slot runeSlot;
    public final Slot filterSlot;
    public final Slot[] displaySlots;

    private final ContainerLevelAccess access;

    public RuneWeaverMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public RuneWeaverMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.RUNE_WEAVER, containerId);
        this.access = access;

        this.pearlSlot = this.addSlot(new PearlInsertSlot(pearlInventory, 0, 8, 34, this));
        this.runeSlot = this.addSlot(new RuneSlot(runeInventory, 0, 26, 34));
        this.filterSlot = this.addSlot(new FilterSlot(filterInventory, 0, 44, 34, this));

        this.displaySlots = new Slot[]{
                this.addSlot(new PearlDisplaySlot(displaysInventory, 0, ModUtil.TOP[0], ModUtil.TOP[1])), // Top
                this.addSlot(new PearlDisplaySlot(displaysInventory, 0, ModUtil.LEFT[0], ModUtil.LEFT[1])), // Left
                this.addSlot(new PearlDisplaySlot(displaysInventory, 0, ModUtil.BOTTOM[0], ModUtil.BOTTOM[1])), // Bottom
                this.addSlot(new PearlDisplaySlot(displaysInventory, 0, ModUtil.RIGHT[0], ModUtil.RIGHT[1])) // Right
        };

        this.addStandardInventorySlots(inventory, 8, 84);

        access.execute((level, pos) -> {
            if (level.getBlockEntity(pos) instanceof RuneWeaverBlockEntity weaverBlock) {
                weaverBlock.setActiveEntity(EntityReference.of(inventory.player));
            }
        });
    }

    public void slotsChanged(Container container) {
        super.slotsChanged(container);
    }

    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> {
            this.clearContainer(player, this.pearlInventory);
            this.clearContainer(player, this.runeInventory);
            this.clearContainer(player, this.filterInventory);

            if (level.getBlockEntity(pos) instanceof RuneWeaverBlockEntity weaverBlock) {
                weaverBlock.setRuneStack(ItemStack.EMPTY);
                weaverBlock.setActiveEntity(null);
            }
        });
    }

    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.RUNE_WEAVER.get());
    }

    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack clicked = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot != null) {
            ItemStack stack = slot.getItem();
            clicked = stack.copy();
            if (slotIndex >= 0 && slotIndex <= 2) {
                if (this.moveItemStackTo(stack, 1, 35, true)) {
                    this.access.execute((level, pos) -> {
                        if (slotIndex == 1 && level.getBlockEntity(pos) instanceof RuneWeaverBlockEntity weaverBlock) {
                            weaverBlock.setRuneStack(ItemStack.EMPTY);
                        }
                    });

                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(stack, clicked);
            } else if (!pearlSlot.hasItem() && pearlSlot.mayPlace(stack)) {
                if (this.moveItemStackTo(stack.copyWithCount(1), 0, 1, false)) {
                    stack.shrink(1);
                    return ItemStack.EMPTY;
                }
            } else if (!runeSlot.hasItem() && runeSlot.mayPlace(stack) && stack.getCount() == 1) {
                if (this.moveItemStackTo(stack, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!filterSlot.hasItem() && filterSlot.mayPlace(stack)) {
                if (this.moveItemStackTo(stack, 0, 3, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == clicked.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return clicked;
    }
}
