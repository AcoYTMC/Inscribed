package net.kognition.inscribed.impl.block.entity;

import net.kognition.inscribed.impl.index.ModBlockEntities;
import net.kognition.inscribed.impl.inventories.RuneWeaverMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
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

    private @Nullable EntityReference<LivingEntity> activeEntity = null;
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

    public @Nullable LivingEntity getActiveEntity() {
        if (activeEntity == null || this.level == null) return null;
        return activeEntity.getEntity(this.level, LivingEntity.class);
    }

    public void setActiveEntity(@Nullable EntityReference<LivingEntity> activeEntity) {
        this.activeEntity = activeEntity;
        sendBlockUpdated();
    }

    public void saveAdditional(ValueOutput view) {
        if (!runeStack.isEmpty()) view.store(RUNE_STACK_KEY, ItemStack.CODEC, runeStack);
        EntityReference.store(activeEntity, view, ACTIVE_ENTITY_KEY);
    }

    public void loadAdditional(ValueInput view) {
        if (runeStack != null) runeStack = view.read(RUNE_STACK_KEY, ItemStack.CODEC).orElse(ItemStack.EMPTY);
        activeEntity = view.contains(ACTIVE_ENTITY_KEY) ? EntityReference.read(view, ACTIVE_ENTITY_KEY) : null;
    }

    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }
}
