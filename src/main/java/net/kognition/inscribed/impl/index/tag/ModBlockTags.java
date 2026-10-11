package net.kognition.inscribed.impl.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * @author AcoYT
 */
public interface ModBlockTags {
    TagBuilder<Block> BUILDER = new TagBuilder<>(Inscribed.MOD_ID, Registries.BLOCK);

    TagKey<Block> MINEABLE_WITH_HAFTED_CELT = BUILDER.register("mineable_with_hafted_celt");
}
