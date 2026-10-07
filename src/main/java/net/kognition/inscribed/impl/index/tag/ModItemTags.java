package net.kognition.inscribed.impl.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * @author AcoYT
 */
public interface ModItemTags {
    TagBuilder<Item> BUILDER = new TagBuilder<>(Inscribed.MOD_ID, Registries.ITEM);

    TagKey<Item> REMOVED = BUILDER.register("removed");
}
