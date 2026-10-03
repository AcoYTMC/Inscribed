package net.kognition.inscribed.impl.block.entity;

import net.kognition.inscribed.impl.index.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/**
 * @author AcoYT
 */
public class RuneWeaverBlockEntity extends BlockEntity implements MenuProvider {
    public RuneWeaverBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.RUNE_WEAVER, worldPosition, blockState);
    }

    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        if (this.level == null) return null;
        return new RuneWeaverMenu(containerId, inventory, ContainerLevelAccess.create(this.level, this.worldPosition), this.level);
    }
}
