package net.kognition.inscribed.mixin.enchant;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.kognition.inscribed.impl.entity.SpikeProjectile;
import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.kognition.inscribed.impl.Inscribed.isDev;

/**
 * @author TheEnderCore
 */
@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Inject(method = "createProjectile", at = @At("HEAD"), cancellable = true)
    void createSpikeProjectile(Level level, LivingEntity shooter, ItemStack heldItem, ItemStack projectile, boolean isCrit, CallbackInfoReturnable<Projectile> cir) {
        if (projectile.is(ModItemTags.SPIKE_PROJECTILE)) {
            cir.setReturnValue(new SpikeProjectile(level, shooter, projectile, heldItem));
        }
    }

    @ModifyReturnValue(method = "getChargeDuration", at = @At("RETURN"))
    private static int devCharge(int original) {
        return isDev() ? 1 : original;
    }
}
