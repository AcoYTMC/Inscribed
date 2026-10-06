package net.kognition.inscribed.mixin;

import net.kognition.inscribed.impl.index.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * @author AcoYT
 */
@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {
    @Inject(method = "causeFallDamage", at = @At("HEAD"))
    private void inscribed$handleCracking(double fallDistance, float damageModifier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        FallingBlockEntity blockEntity = (FallingBlockEntity)(Object)this;
        Level level = blockEntity.level();

        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, blockEntity.getBoundingBox(), e -> e.getItem().is(Items.NAUTILUS_SHELL));
        if (entities.isEmpty() || !blockEntity.getBlockState().is(BlockTags.ANVIL)) return;

        for (ItemEntity entity : entities) {
            ItemStack stack = entity.getItem();
            Vec3 pos = entity.position();

            level.playSound(
                    blockEntity, blockEntity.blockPosition(),
                    SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS,
                    1.0F, 1.0F
            );

            if (level instanceof ServerLevel serverLevel) {
                entity.discard();

                Containers.dropContents(
                        serverLevel, new BlockPos.MutableBlockPos(pos.x, pos.y, pos.z),
                        NonNullList.withSize(1, ModItems.PEARL.getDefaultInstance().copyWithCount(stack.getCount()))
                );

                serverLevel.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CONDUIT.defaultBlockState()),
                        pos.x, pos.y, pos.z,
                        20,
                        0.15, 0.15, 0.15,
                        1.0
                );
            }
        }
    }
}
