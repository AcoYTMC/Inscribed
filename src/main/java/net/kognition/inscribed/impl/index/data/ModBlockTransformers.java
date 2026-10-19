package net.kognition.inscribed.impl.index.data;

import com.google.common.collect.ImmutableList;
import net.acoyt.acornlib.api.builder.KeyedBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.Direction;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

import java.util.List;

/**
 * @author AcoYT
 */
public interface ModBlockTransformers {
    KeyedBuilder<BlockTransformer> BUILDER = new KeyedBuilder<>(Inscribed.MOD_ID, Registries.BLOCK_TRANSFORMER);

    ResourceKey<BlockTransformer> HAFTED_CELT = BUILDER.register("hafted_celt", new BlockTransformer(
                    ImmutableList.<BlockTransformer.BlockTransformData>builder()
                            .addAll(List.of(BlockTransformers.axeStrippables()))
                            .addAll(BlockTransformers.axe(WeatheringCopper.PREVIOUS_BY_BLOCK.get().entrySet(), SoundEvents.AXE_SCRAPE, BlockTransformer.TransformParticle.SCRAPE))
                            .addAll(BlockTransformers.axe(HoneycombItem.WAX_OFF_BY_BLOCK.get().entrySet(), SoundEvents.AXE_WAX_OFF, BlockTransformer.TransformParticle.WAX_OFF))
                            .add(BlockTransformer.BlockTransformData.builder(
                                                    BlockPredicate.allOf(
                                                            BlockPredicate.matchesTag(BlockTags.TURNS_INTO_DIRT_PATH),
                                                            BlockPredicate.matchesTag(Direction.UP, BlockTags.AIR)
                                                    ),
                                                    Blocks.DIRT_PATH
                                            ).sound(SoundEvents.SHOVEL_FLATTEN)
                                            .disallowedFaces(List.of(Direction.DOWN))
                                            .build()
                            ).build()
            )
    );
}
