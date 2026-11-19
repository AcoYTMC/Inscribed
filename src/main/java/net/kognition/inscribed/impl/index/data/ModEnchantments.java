package net.kognition.inscribed.impl.index.data;

import net.acoyt.acornlib.api.builder.KeyedBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.NonNull;

import java.util.function.BiFunction;

import static net.kognition.inscribed.impl.Inscribed.id;

/**
 * @author TheEnderCore
 */
public interface ModEnchantments {
    // TODO: Switch to vanilla-ish registry
    KeyedBuilder<Enchantment> BUILDER = new KeyedBuilder<>(Inscribed.MOD_ID, Registries.ENCHANTMENT);

    ResourceKey<Enchantment> SIPHON = register("siphon", (items, _) -> Enchantment.enchantment(simpleDefinition(items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE), EquipmentSlotGroup.HEAD)));
    ResourceKey<Enchantment> VENDETTA = register("vendetta", (items, _) -> Enchantment.enchantment(simpleDefinition(items.getOrThrow(ItemTags.TRIDENT_ENCHANTABLE), EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND)));
    ResourceKey<Enchantment> HUNTER = register("hunter", (items, _) -> Enchantment.enchantment(simpleDefinition(items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE), EquipmentSlotGroup.HEAD)));
    ResourceKey<Enchantment> WEAVING = register("weaving", (items, _) -> Enchantment.enchantment(simpleDefinition(items.getOrThrow(ItemTags.CHEST_ARMOR_ENCHANTABLE), EquipmentSlotGroup.CHEST)));
    ResourceKey<Enchantment> LASSO = register("lasso", (items, _) -> Enchantment.enchantment(simpleDefinition(items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE), EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND)));
    ResourceKey<Enchantment> THRUST = register("thrust", (items, _) -> Enchantment.enchantment(simpleDefinition(items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE), EquipmentSlotGroup.HEAD)));
    ResourceKey<Enchantment> RICOSHATTER = register("ricoshatter", (items, _) -> Enchantment.enchantment(simpleDefinition(items.getOrThrow(ItemTags.CROSSBOW_ENCHANTABLE), EquipmentSlotGroup.MAINHAND, EquipmentSlotGroup.OFFHAND)));

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

    private static ResourceKey<Enchantment> register(String name, BiFunction<HolderGetter<Item>, HolderGetter<Enchantment>, Enchantment.Builder> builder) {
        return BUILDER.register(name, (context) -> {
            var items = context.lookup(Registries.ITEM);
            var enchantments = context.lookup(Registries.ENCHANTMENT);
            return builder.apply(items, enchantments).build(id(name));
        });
    }
}
