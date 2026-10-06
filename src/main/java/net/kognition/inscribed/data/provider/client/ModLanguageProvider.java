package net.kognition.inscribed.data.provider.client;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.kognition.inscribed.impl.index.ModBlocks;
import net.kognition.inscribed.impl.index.ModItems;
import net.kognition.inscribed.impl.index.ModSounds;
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

        // Sounds
        builder.add(ModSounds.RUNE_WEAVER_OPEN, "Rune Weaver opens");
        builder.add(ModSounds.RUNE_WEAVER_BREAK, "Rune Weaver broken");
        builder.add(ModSounds.RUNE_WEAVER_PLACE, "Rune Weaver placed");

        builder.add(ModSounds.RUNE_WEAVER_SELECT, "Rune Weaver selects pearl");
        builder.add(ModSounds.RUNE_WEAVER_DESELECT, "Rune Weaver deselects pearl");

        builder.add(ModSounds.RUNE_WEAVER_CONFIRM, "Rune Weaver confirms pearl");
        builder.add(ModSounds.RUNE_WEAVER_UNAVAILABLE, "Rune Weaver pearl is unavailable");
    }
}
