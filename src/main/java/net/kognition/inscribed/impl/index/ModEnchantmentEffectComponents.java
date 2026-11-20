package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.template.RegistrantBase;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;

import java.util.function.UnaryOperator;

/**
 * @author TheEnderCore
 */
public interface ModEnchantmentEffectComponents {
    RegistrantBase<DataComponentType<?>> COMPONENTS = new RegistrantBase<>(Inscribed.MOD_ID, BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE) {
        @Override
        public void registerLang(HolderLookup.Provider provider, FabricLanguageProvider.TranslationBuilder builder) {
        }
    };

    DataComponentType<Unit> WEAVING = register("weaving", b -> b.persistent(Unit.CODEC));
    DataComponentType<Unit> RICOSHATTER = register("ricoshatter", b -> b.persistent(Unit.CODEC));

    static void init() {
    }

    private static <T> DataComponentType<T> register(final String name, final UnaryOperator<DataComponentType.Builder<T>> builder) {
        return COMPONENTS.register(name, builder.apply(DataComponentType.builder()).build());
    }
}
