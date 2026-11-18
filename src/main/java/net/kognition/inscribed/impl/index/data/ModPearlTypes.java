package net.kognition.inscribed.impl.index.data;

import net.acoyt.acornlib.api.builder.KeyedBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.kognition.inscribed.impl.util.data.PearlCategory;
import net.kognition.inscribed.impl.util.data.PearlType;
import net.minecraft.resources.ResourceKey;

/**
 * @author TheEnderCore
 */
public interface ModPearlTypes {
    KeyedBuilder<PearlType> BUILDER = new KeyedBuilder<>(Inscribed.MOD_ID, Inscribed.PEARL_DATA_KEY);

    // TODO: Should these be named provider or type?
    ResourceKey<PearlType> DEXTERITY_PROVIDER = BUILDER.register("dexterity_provider", new PearlType(
            ModItemTags.DEXTERITY_PROVIDER, PearlCategory.DEXTERITY
    ));
    ResourceKey<PearlType> PRESERVATION_PROVIDER = BUILDER.register("preservation_provider", new PearlType(
            ModItemTags.PRESERVATION_PROVIDER, PearlCategory.PRESERVATION
    ));
    ResourceKey<PearlType> RESTORATION_PROVIDER = BUILDER.register("restoration_provider", new PearlType(
            ModItemTags.RESTORATION_PROVIDER, PearlCategory.RESTORATION
    ));
    ResourceKey<PearlType> RIGOROUS_PROVIDER = BUILDER.register("rigorous_provider", new PearlType(
            ModItemTags.RIGOROUS_PROVIDER, PearlCategory.RIGOROUS
    ));
}
