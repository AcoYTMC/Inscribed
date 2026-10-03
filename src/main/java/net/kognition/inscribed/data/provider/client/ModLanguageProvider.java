package net.kognition.inscribed.data.provider.client;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.kognition.inscribed.impl.index.ModBlocks;
import net.kognition.inscribed.impl.index.ModItems;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        ModBlocks.BLOCKS.registerLang(provider, builder);
        ModItems.ITEMS.registerLang(provider, builder);

        builder.add("container.inscribed.rune_weaver", "Rune Weaver");

        builder.add("itemGroup.inscribed", "Inscribed");
    }
}
