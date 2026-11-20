package net.kognition.inscribed.mixin.enchant;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.kognition.inscribed.impl.index.ModEnchantmentEffectComponents;
import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Predicate;

/**
 * @author TheEnderCore
 */
@Mixin(Player.class)
public class PlayerMixin {
    @ModifyExpressionValue(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;getSupportedHeldProjectiles()Ljava/util/function/Predicate;"))
    Predicate<ItemStack> allowSpikes(Predicate<ItemStack> original, final ItemStack heldWeapon) {
        return EnchantmentHelper.has(heldWeapon, ModEnchantmentEffectComponents.RICOSHATTER) ? original.or((stack) -> stack.is(ModItemTags.SPIKE_PROJECTILE)) : original;
    }

    @ModifyExpressionValue(method = "getProjectile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;getAllSupportedProjectiles()Ljava/util/function/Predicate;"))
    Predicate<ItemStack> allowSpikes2(Predicate<ItemStack> original, final ItemStack heldWeapon) {
        return EnchantmentHelper.has(heldWeapon, ModEnchantmentEffectComponents.RICOSHATTER) ? original.or((stack) -> stack.is(ModItemTags.SPIKE_PROJECTILE)) : original;
    }
}
