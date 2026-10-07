package net.kognition.inscribed.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author AcoYT
 */
@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {
    @WrapOperation(
            method = "repairPlayerItems",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRandomItemWith(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/Optional;"
            )
    )
    private Optional<EnchantedItemInUse> inscribed$passiveMending(DataComponentType<?> componentType, LivingEntity source, Predicate<ItemStack> predicate, Operation<Optional<EnchantedItemInUse>> original) {
        if (componentType == EnchantmentEffectComponents.REPAIR_WITH_XP) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = source.getItemBySlot(slot);
                if (predicate.test(stack)) {
                    return Optional.of(new EnchantedItemInUse(stack, slot, source));
                }
            }
        }

        return original.call(componentType, source, predicate);
    }
}
