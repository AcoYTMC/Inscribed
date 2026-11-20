package net.kognition.inscribed.data.provider.server;

import net.kognition.inscribed.impl.index.ModEnchantmentEffectComponents;
import net.kognition.inscribed.impl.index.data.ModEnchantments;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import org.jspecify.annotations.NonNull;

/**
 * @author TheEnderCore
 */
public class ModEnchantmentProvider {
    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var items = context.lookup(Registries.ITEM);
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        register(context, ModEnchantments.SIPHON, Enchantment
                .enchantment(
                        simpleDefinition(items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE), EquipmentSlotGroup.HEAD)
                )
        );
        register(context, ModEnchantments.VENDETTA, Enchantment
                .enchantment(
                        simpleDefinition(items.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE), EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND)
                )
        );
        register(context, ModEnchantments.HUNTER, Enchantment
                .enchantment(
                        simpleDefinition(items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE), EquipmentSlotGroup.HEAD)
                )
        );
        register(context, ModEnchantments.WEAVING, Enchantment
                .enchantment(
                        simpleDefinition(items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE), EquipmentSlotGroup.CHEST)
                )
                .withEffect(ModEnchantmentEffectComponents.WEAVING)
        );
        register(context, ModEnchantments.LASSO, Enchantment
                .enchantment(
                        simpleDefinition(items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE), EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND)
                )
        );
        register(context, ModEnchantments.THRUST, Enchantment
                .enchantment( // TODO add a spears tag
                        simpleDefinition(items.getOrThrow(ItemTags.LUNGE_ENCHANTABLE), EquipmentSlotGroup.MAINHAND)
                )
        );
        register(context, ModEnchantments.RICOSHATTER, Enchantment
                .enchantment(
                        simpleDefinition(items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE), EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND)
                )
                .withEffect(ModEnchantmentEffectComponents.RICOSHATTER)
                .withEffect(EnchantmentEffectComponents.PROJECTILE_PIERCING, new AddValue(LevelBasedValue.perLevel(1.0F)))
        );

    }

    private static Enchantment.@NonNull EnchantmentDefinition simpleDefinition(HolderSet.Named<Item> tag, EquipmentSlotGroup... group) {
        return Enchantment.definition(
                tag,
                1, 1,
                Enchantment.constantCost(15),
                Enchantment.constantCost(65),
                8,
                group
        );
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder enchantment) {
        context.register(key, enchantment.build(key.identifier()));
    }
}
