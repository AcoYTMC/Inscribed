package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.data.ModBlockTransformers;
import net.kognition.inscribed.impl.index.ref.ModToolMaterials;
import net.kognition.inscribed.impl.index.tag.ModBlockTags;
import net.kognition.inscribed.impl.item.ChiselItem;
import net.kognition.inscribed.impl.item.RuneItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;

/**
 * @author AcoYT
 */
public interface ModItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Inscribed.MOD_ID);

    Item RUNE = ITEMS.register("rune", RuneItem::new, new Item.Properties()
            .fireResistant()
            .stacksTo(1));

    Item PEARL = ITEMS.register("pearl", Item::new, new Item.Properties()
            .stacksTo(16));

    Item HAFTED_CELT = ITEMS.register("hafted_celt", Item::new, new Item.Properties()
            .tool(ModToolMaterials.FLINT, ModBlockTags.MINEABLE_WITH_HAFTED_CELT, 4.0F, -3.0F, 5.0F)
            .delayedComponent(DataComponents.BLOCK_TRANSFORMER, context -> context.getOrThrow(ModBlockTransformers.HAFTED_CELT)));

    Item CHISEL = ITEMS.register("chisel", ChiselItem::new, new Item.Properties()
            .tool(ToolMaterial.IRON, BlockTags.SWORD_INSTANTLY_MINES, 0.0F, -3.2F, 0.0F));

    static void init() {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                Items.NAUTILUS_SHELL,
                builder -> builder.set(DataComponents.MAX_STACK_SIZE, 16) // blehhh... so what!! who cares!! I got lazy and I hate math!!
        ));
    }
}
