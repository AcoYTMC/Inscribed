package net.kognition.inscribed.data.provider.server;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.util.data.PearlType;
import net.kognition.inscribed.impl.util.data.PearlType.Pearl;
import net.kognition.inscribed.impl.util.data.PearlTypeReloadListener;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @author AcoYT
 */
public class ModPearlTypeProvider extends FabricCodecDataProvider<PearlType> {
    public ModPearlTypeProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture, PackOutput.Target.DATA_PACK, PearlTypeReloadListener.PATH, PearlType.DIRECT_CODEC);
    }

    public void configure(BiConsumer<Identifier, PearlType> consumer, HolderLookup.Provider provider) {
        Pearl[] preservation = new Pearl[]{new Pearl(true), new Pearl(false), new Pearl(true)};

        builder(consumer, ItemTags.SWORDS);
        builder(consumer, ItemTags.AXES);
        builder(consumer, ItemTags.HOES);
        builder(consumer, ItemTags.PICKAXES);
        builder(consumer, ItemTags.SHOVELS);
        builder(consumer, ItemTags.SPEARS);

        builder(consumer, ConventionalItemTags.MACE_TOOLS);
        builder(consumer, ConventionalItemTags.SHIELD_TOOLS, preservation);

        builder(consumer, ItemTags.FOOT_ARMOR, preservation);
        builder(consumer, ItemTags.LEG_ARMOR, preservation);
        builder(consumer, ItemTags.CHEST_ARMOR, preservation);
        builder(consumer, ItemTags.HEAD_ARMOR, preservation);
    }

    public static void builder(BiConsumer<Identifier, PearlType> consumer, TagKey<Item> tag, Pearl... pearls) {
        consumer.accept(tag.location(), new PearlType(tag, List.of(pearls)));
    }

    public String getName() {
        return Inscribed.MOD_ID + "_types";
    }
}
