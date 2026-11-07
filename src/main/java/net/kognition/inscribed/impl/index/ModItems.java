package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.data.ModBlockTransformers;
import net.kognition.inscribed.impl.index.ref.ModItemIds;
import net.kognition.inscribed.impl.index.ref.ModToolMaterials;
import net.kognition.inscribed.impl.index.tag.ModBlockTags;
import net.kognition.inscribed.impl.item.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

/**
 * @author AcoYT
 */
public interface ModItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Inscribed.MOD_ID);

    Item RUNE = register(ModItemIds.RUNE, RuneItem::new, new Item.Properties()
            .fireResistant()
            .stacksTo(1));

    Item PEARL = register(ModItemIds.PEARL, Item::new, new Item.Properties()
            .stacksTo(16));

    Item ROPE = register(ModItemIds.ROPE, RopeItem::new, new Item.Properties()
            .stacksTo(1));

    // Tools
    Item HAFTED_CELT = register(ModItemIds.HAFTED_CELT, HaftedCeltItem::new, new Item.Properties()
            .tool(ModToolMaterials.FLINT, ModBlockTags.MINEABLE_WITH_HAFTED_CELT, 4.0F, -3.0F, 5.0F)
            .delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(ModBlockTransformers.HAFTED_CELT))
            .customDamage(HaftedCeltItem::hurtAndBreak));

    Item CHISEL = register(ModItemIds.CHISEL, ChiselItem::new, new Item.Properties()
            .tool(ToolMaterial.IRON, BlockTags.SWORD_INSTANTLY_MINES, 0.0F, -2.2F, 0.0F));

    Item BLOODLINE = register(ModItemIds.BLOODLINE, BloodlineItem::new, new Item.Properties()
            .stacksTo(1));

    private static Item register(ResourceKey<Item> key, Function<Item.Properties, Item> factory, Item.Properties properties) {
        return ITEMS.register(key.identifier().getPath(), factory, properties);
    }

    static void init() {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                Items.NAUTILUS_SHELL,
                builder -> builder.set(DataComponents.MAX_STACK_SIZE, 16) // blehhh... so what!! who cares!! I got lazy and I hate math!!
        ));
    }
}
