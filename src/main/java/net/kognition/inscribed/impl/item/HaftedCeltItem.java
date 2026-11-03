package net.kognition.inscribed.impl.item;

import net.kognition.inscribed.api.item.ExtraBarItem;
import net.kognition.inscribed.impl.component.ClusteredComponent;
import net.kognition.inscribed.impl.index.ModDataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * @author AcoYT
 */
public class HaftedCeltItem extends Item implements ExtraBarItem {
    public HaftedCeltItem(Properties properties) {
        super(properties);
    }

    public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack otherStack, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
        ItemStack other = carriedItem.get();

        if (clickAction == ClickAction.SECONDARY && !ClusteredComponent.get(self).isFullyCharged() && other.is(Items.AMETHYST_SHARD)) {
            ClusteredComponent.getModifiable(self, true).charges(30).apply();
            player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 1.0F, 1.0F);

            other.shrink(1);
            return true;
        }

        return false;
    }

    public boolean isExtraBarVisible(ItemStack stack) {
        if (!stack.has(ModDataComponents.CLUSTERED)) return false;
        return ClusteredComponent.get(stack).charges() > 0;
    }

    public int getExtraBarColor(ItemStack stack) {
        if (!stack.has(ModDataComponents.CLUSTERED)) return 0;
        return 0xFFc798ec;
    }

    public int getExtraBarWidth(ItemStack stack) {
        if (!stack.has(ModDataComponents.CLUSTERED)) return 0;
        ClusteredComponent component = ClusteredComponent.get(stack);
        return Mth.clamp(Math.round((float) component.charges() / component.maxCharges() * 13), 0, 13);
    }

    public static int hurtAndBreak(ItemStack stack, int amount, LivingEntity entity, EquipmentSlot slot, Runnable breakCallback) {
        if (slot == EquipmentSlot.MAINHAND && stack.has(ModDataComponents.CLUSTERED)) {
            ClusteredComponent.getModifiable(stack, true).increment(-amount).apply();
            if (ClusteredComponent.get(stack).charges() <= 0) {
                stack.remove(ModDataComponents.CLUSTERED);
                if (entity instanceof Player player) player.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 1.0F, 1.0F);
            } else if (entity instanceof Player player) {
                player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 2.0F, 1.0F);
                player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 1.0F, 1.0F);
            }

            return 0;
        }

        return amount;
    }
}
