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

    TagKey<Item> DEXTERITY_PROVIDER = BUILDER.register("dexterity_provider");
    TagKey<Item> PRESERVATION_PROVIDER = BUILDER.register("preservation_provider");
    TagKey<Item> RESTORATION_PROVIDER = BUILDER.register("restoration_provider");
    TagKey<Item> RIGOROUS_PROVIDER = BUILDER.register("rigorous_provider");
}
