package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.template.RegistrantBase;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

/**
 * @author AcoYT
 */
public interface ModStats {
    RegistrantBase<Identifier> STATS = new RegistrantBase<>(Inscribed.MOD_ID, BuiltInRegistries.CUSTOM_STAT) {
        public void registerLang(HolderLookup.Provider provider, FabricLanguageProvider.TranslationBuilder builder) {
            //
        }
    };

    Identifier INTERACT_WITH_RUNE_WEAVER = create("interact_with_rune_weaver", StatFormatter.DEFAULT);

    static Identifier create(String id, StatFormatter formatter) {
        Identifier location = Inscribed.id(id);
        STATS.register(id, location);
        Stats.CUSTOM.get(location, formatter);
        return location;
    }

    static void init() {}
}
