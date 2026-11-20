package net.kognition.inscribed.impl.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;

/**
 * @author TheEnderCore
 */
public interface ExtendedAbstractArrow {
    DamageSource createDamageSource(final AbstractArrow arrow, final @Nullable Entity owner);
    boolean hasCustomBlockHit();
    default void customBlockHit(final HitResult blockHitResult) {}
}
