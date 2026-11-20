package net.kognition.inscribed.data.provider.client;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.kognition.inscribed.impl.index.*;
import net.kognition.inscribed.impl.index.data.ModEnchantments;
import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.kognition.inscribed.impl.util.data.PearlCategory;
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
        ModEntities.ENTITIES.registerLang(provider, builder);
        ModStats.STATS.registerLang(provider, builder);

        ModItemTags.BUILDER.registerLang(provider, builder);

        ModEnchantments.ENCHANTMENTS.forEach((key) -> builder.addEnchantment(key, MiscUtils.formatString(key.identifier().getPath())));

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

        builder.add("tooltip.inscribed.pearl_category", "%s | %s");

        for (PearlCategory category : PearlCategory.values()) {
            String categoryName = category.getSerializedName();
            builder.add("inscribed.category." + categoryName, MiscUtils.formatString(categoryName));

            for (PearlCategory.Type type : category.getPairs()) {
                builder.add("inscribed.category." + categoryName + "." + type.name(), MiscUtils.formatString(type.name()));
            }
        }
    }
}
