package net.kognition.inscribed.mixin.enchant.hide;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

import static net.kognition.inscribed.impl.Inscribed.CONFIG;

/**
 * @author TheEnderCore
 */
@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "updateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/ItemEnchantments$Mutable;toImmutable()Lnet/minecraft/world/item/enchantment/ItemEnchantments;"))
    private static void hideEnchantBooks(ItemStack itemStack, Consumer<ItemEnchantments.Mutable> consumer, CallbackInfoReturnable<ItemEnchantments> cir, @Local(name = "mutableEnchantments") ItemEnchantments.Mutable mutableEnchantments) {
        mutableEnchantments.removeIf(holder -> holder.unwrapKey().map(key -> !CONFIG.isEnchantAllowed(key.identifier())).orElse(false));
    }
}
