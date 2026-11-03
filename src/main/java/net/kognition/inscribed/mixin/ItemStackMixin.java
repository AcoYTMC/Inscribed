package net.kognition.inscribed.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.kognition.inscribed.impl.component.ClusteredComponent;
import net.kognition.inscribed.impl.index.ModDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

/**
 * @author AcoYT
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @WrapOperation(
            method = "addDetailsToTooltip",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V",
                    ordinal = 0
            )
    )
    private <T> void inscribed$addRuneTooltip(ItemStack instance, DataComponentType<T> type, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag, Operation<Void> original) {
        original.call(instance, ModDataComponents.RUNE, context, display, consumer, flag);
        original.call(instance, type, context, display, consumer, flag);
    }

    @WrapMethod(method = "hurtAndBreak(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/function/Consumer;)V")
    private void inscribed$useClusteredChargesInstead(int amount, ServerLevel level, @Nullable ServerPlayer player, Consumer<ItemStack> onBreak, Operation<Void> original) {
        ItemStack stack = (ItemStack) (Object) this;
        if (stack.has(ModDataComponents.CLUSTERED)) {
            ClusteredComponent.getModifiable(stack, true).increment(-1).apply();
            if (ClusteredComponent.get(stack).charges() == 0) {
                stack.remove(ModDataComponents.CLUSTERED);
                if (player != null) player.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1.0F, 1.0F);
            } else if (player != null) {
                player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 2.0F, 1.0F);
                player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 1.0F, 1.0F);
            }

            return;
        }

        original.call(amount, level, player, onBreak);
    }
}
