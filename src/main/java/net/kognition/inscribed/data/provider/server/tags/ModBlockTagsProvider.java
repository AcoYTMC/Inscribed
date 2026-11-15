package net.kognition.inscribed.data.provider.server.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.kognition.inscribed.impl.index.ref.ModBlockItemIds;
import net.kognition.inscribed.impl.index.tag.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    public void addTags(HolderLookup.Provider provider) {
        this.builder(ModBlockTags.MINEABLE_WITH_HAFTED_CELT)
                .forceAddTag(BlockTags.MINEABLE_WITH_AXE)
                .forceAddTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .forceAddTag(BlockTags.MINEABLE_WITH_SHOVEL);

        this.builder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlockItemIds.RUNE_WEAVER);

        this.builder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlockItemIds.RUNE_WEAVER);
    }
}
