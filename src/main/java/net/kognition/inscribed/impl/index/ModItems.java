package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.item.RuneItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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

    static void init() {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                Items.NAUTILUS_SHELL,
                builder -> builder.set(DataComponents.MAX_STACK_SIZE, 16) // blehhh... so what!! who cares!! I got lazy and I hate math!!
        ));
    }
}
