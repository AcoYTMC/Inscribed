package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.item.RuneItem;
import net.minecraft.world.item.Item;

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

    static void init() {}
}
