package net.kognition.inscribed.data.provider.server;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.kognition.inscribed.impl.util.data.PearlCategory;
import net.kognition.inscribed.impl.util.data.PearlType;
import net.kognition.inscribed.impl.util.data.PearlTypeReloadListener;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

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
        builder(consumer, ModItemTags.DEXTERITY_PROVIDER, PearlCategory.DEXTERITY);
        builder(consumer, ModItemTags.PRESERVATION_PROVIDER, PearlCategory.PRESERVATION);
        builder(consumer, ModItemTags.RESTORATION_PROVIDER, PearlCategory.RESTORATION);
        builder(consumer, ModItemTags.RIGOROUS_PROVIDER, PearlCategory.RIGOROUS);
    }

    public static void builder(BiConsumer<Identifier, PearlType> consumer, TagKey<Item> tag, PearlCategory category) {
        consumer.accept(tag.location(), new PearlType(tag, category));
    }

    public String getName() {
        return Inscribed.MOD_ID + "_types";
    }
}
