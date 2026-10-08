package net.kognition.inscribed.data.provider.server.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class ModItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    public void addTags(HolderLookup.Provider provider) {
        this.builder(ModItemTags.REMOVED)
                .add(ItemIds.STONE_AXE, ItemIds.STONE_SHOVEL, ItemIds.STONE_PICKAXE, ItemIds.STONE_HOE, ItemIds.STONE_SWORD, ItemIds.STONE_SPEAR)
                .setReplace(false);

        this.builder(ModItemTags.DEXTERITY_PROVIDER)
                .setReplace(false);

        this.builder(ModItemTags.PRESERVATION_PROVIDER)
                .forceAddTag(ConventionalItemTags.SHIELD_TOOLS)
                .forceAddTag(ItemTags.FOOT_ARMOR)
                .forceAddTag(ItemTags.LEG_ARMOR)
                .forceAddTag(ItemTags.CHEST_ARMOR)
                .forceAddTag(ItemTags.HEAD_ARMOR)
                .setReplace(false);

        this.builder(ModItemTags.RESTORATION_PROVIDER)
                .add(ItemIds.EXPERIENCE_BOTTLE)
                .setReplace(false);

        this.builder(ModItemTags.RIGOROUS_PROVIDER)
                .setReplace(false);
    }
}
