package net.kognition.inscribed.impl.index.ref;

import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

/**
 * @author AcoYT
 */
public interface ModItemIds {
    ResourceKey<Item> RUNE = create("rune");
    ResourceKey<Item> PEARL = create("pearl");
    ResourceKey<Item> ROPE = create("rope");

    ResourceKey<Item> HAFTED_CELT = create("hafted_celt");
    ResourceKey<Item> CHISEL = create("chisel");
    ResourceKey<Item> BLOODLINE = create("bloodline");

    private static ResourceKey<Item> create(String id) {
        return ResourceKey.create(Registries.ITEM, Inscribed.id(id));
    }
}
