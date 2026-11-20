package net.kognition.inscribed.impl.entity;

import net.acoyt.acornlib.impl.index.AcornSounds;
import net.kognition.inscribed.impl.index.ModEntities;
import net.kognition.inscribed.impl.index.data.ModDamageTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * @author TheEnderCore
 */
public class SpikeProjectile extends AbstractArrow implements ExtendedAbstractArrow {
    private static final EntityDataAccessor<ItemStack> RENDER_STACK = SynchedEntityData.defineId(SpikeProjectile.class, EntityDataSerializers.ITEM_STACK);
    public int iteration = 0;

    public SpikeProjectile(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public SpikeProjectile(double x, double y, double z, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.SPIKE, x, y, z, level, pickupItemStack, firedFromWeapon);
        setRenderStack(getPickupItem());
    }

    public SpikeProjectile(Level level, LivingEntity mob, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.SPIKE, mob, level, pickupItemStack, firedFromWeapon);
        setRenderStack(getPickupItem());
    }

    @Override
    protected void defineSynchedData(final SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(RENDER_STACK, getDefaultPickupItem());
    }

    public ItemStack getRenderStack() {
        return this.entityData.get(RENDER_STACK);
    }

    public void setRenderStack(final ItemStack stack) {
        this.entityData.set(RENDER_STACK, stack);
    }

    @Override
    protected void readAdditionalSaveData(final ValueInput input) {
        super.readAdditionalSaveData(input);
        setRenderStack(getPickupItem());
    }

    @Override
    public boolean hasCustomBlockHit() {
        return true;
    }

    @Override
    public void customBlockHit(HitResult hitResult) {
        if (!(hitResult instanceof BlockHitResult hit)) {
            return;
        }
        Direction direction = hit.getDirection();
        var dirNormal = direction.getUnitVec3();
        var isBorder = hit.isWorldBorderHit();

        Vec3 delta = getDeltaMovement();
        Vec3 reflectedDelta = delta.add(
                dirNormal.scale(
                        dirNormal.dot(delta) / dirNormal.dot(dirNormal)
                ).scale(-2.0)
        ).scale(isBorder ? 1.0 : 0.85);
        if (iteration < 3) {
            var stack = getRenderStack();
            var breakSound = stack.get(DataComponents.BREAK_SOUND);
            if (stack.getItem() instanceof BlockItem blockItem) {
                var block = blockItem.getBlock();
                playSound(block.defaultBlockState().getSoundType().getBreakSound(), 1f, 1f);
            } else if (breakSound != null) playSound(breakSound.value(), 1f, 1f);

            split(reflectedDelta);
        } else {
            // TODO do break anim
        }
        if (isBorder) {
            playSound(AcornSounds.CLOWN_ACO_PLUSH_HONK, 5f, 1f);
        }

        discard();
    }

    private void split(Vec3 reflectedDelta) {
        float maxAngle = 30f;
        float angleStep = (float) (2.0F * maxAngle / (0.45 - 1f));
        float direction = 1.0F;

        for (int i = 0; i < 3; i++) {
            var spike = new SpikeProjectile(level(), (LivingEntity) getOwner(), getPickupItem(), getWeaponItem());
            spike.setPos(position());
            spike.iteration = iteration + 1;
            float angle = direction * ((i + 1) / 2f) * angleStep;
            spike.setDeltaMovement(reflectedDelta.add(this.random.triangle(0.0, 0.0172275 * angle)));

            var movement = spike.getDeltaMovement();
            float yRot = (float) (Mth.atan2(movement.x, movement.z) * 180.0F / (float) Math.PI);
            float xRot = (float) (Mth.atan2(movement.y, movement.horizontalDistance()) * 180.0F / (float) Math.PI);
            spike.setXRot(xRot);
            spike.setYRot(yRot);

            level().addFreshEntity(spike);
        }
    }

    @Override
    public ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.SULFUR_SPIKE);
    }

    @Override
    public DamageSource createDamageSource(AbstractArrow arrow, @Nullable Entity owner) {
        return damageSources().source(ModDamageTypes.SPIKED, arrow, owner);
    }
}
