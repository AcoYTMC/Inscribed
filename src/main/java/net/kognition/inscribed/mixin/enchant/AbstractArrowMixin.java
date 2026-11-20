package net.kognition.inscribed.mixin.enchant;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.kognition.inscribed.impl.entity.ExtendedAbstractArrow;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author TheEnderCore
 */
@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {
    @WrapOperation(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSources;arrow(Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/damagesource/DamageSource;"))
    DamageSource doCustomDamage(DamageSources instance, AbstractArrow arrow, Entity owner, Operation<DamageSource> original) {
        return arrow instanceof ExtendedAbstractArrow betterArrow ? betterArrow.createDamageSource(arrow, owner) : original.call(instance, arrow, owner);
    }

    @WrapOperation(method = "stepMoveAndHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"))
    ProjectileDeflection modifyBlockHit(AbstractArrow arrow, HitResult hit, Operation<ProjectileDeflection> original) {
        if (arrow instanceof ExtendedAbstractArrow betterArrow && betterArrow.hasCustomBlockHit()) {
            betterArrow.customBlockHit(hit);
            return ProjectileDeflection.NONE;
        }
        return original.call(arrow, hit);
    }

}
