package net.kognition.inscribed.impl.block.entity;

import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.ModBlockEntities;
import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

/**
 * @author AcoYT
 */
public class RuneWeaverBlockEntity extends BlockEntity implements MenuProvider {
    private ItemStack runeStack = ItemStack.EMPTY;
    private static final String RUNE_STACK_KEY = "RuneStack";

    private int activeEntity = -1;
    private static final String ACTIVE_ENTITY_KEY = "ActiveEntity";

    public RuneWeaverBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.RUNE_WEAVER, worldPosition, blockState);
    }

    public void sendBlockUpdated() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_INVISIBLE);
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide()) {
            Entity entity = level.getEntity(activeEntity);
            if (entity == null || activeEntity == -1) {
                if (activeEntity != -1) {
                    Inscribed.LOGGER.debug("Entity with id {} was missing, cleared data!", activeEntity);
                }

                activeEntity = -1;
                runeStack = ItemStack.EMPTY;
                sendBlockUpdated();
            }
        }
    }

    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        if (this.level == null) return null;
        return new RuneWeaverMenu(containerId, inventory, ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    public ItemStack getRuneStack() {
        return runeStack;
    }

    public void setRuneStack(ItemStack runeStack) {
        this.runeStack = runeStack;
        sendBlockUpdated();
    }

    public @Nullable Entity getActiveEntity() {
        if (activeEntity == -1 || this.level == null) return null;
        return this.level.getEntity(activeEntity);
    }

    public void setActiveEntity(@Nullable Entity activeEntity) {
        this.activeEntity = activeEntity == null ? -1 : activeEntity.getId();
        sendBlockUpdated();
    }

    public void saveAdditional(ValueOutput view) {
        if (!runeStack.isEmpty()) view.store(RUNE_STACK_KEY, ItemStack.CODEC, runeStack);
        view.putInt(ACTIVE_ENTITY_KEY, activeEntity);
    }

    public void loadAdditional(ValueInput view) {
        if (runeStack != null) runeStack = view.read(RUNE_STACK_KEY, ItemStack.CODEC).orElse(ItemStack.EMPTY);
        activeEntity = view.getIntOr(ACTIVE_ENTITY_KEY, -1);
    }

    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }
}
