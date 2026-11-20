package net.kognition.inscribed.impl.index.data;

import net.acoyt.acornlib.api.builder.KeyedBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

/**
 * @author TheEnderCore
 */
// TODO: Add proper translations
public interface ModDamageTypes {
    KeyedBuilder<DamageType> BUILDER = new KeyedBuilder<>(Inscribed.MOD_ID, Registries.DAMAGE_TYPE);

    ResourceKey<DamageType> SPIKED = BUILDER.register("spiked", new DamageType("spiked", 0f));
}
