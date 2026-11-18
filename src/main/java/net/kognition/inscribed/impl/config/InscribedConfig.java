package net.kognition.inscribed.impl.config;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.kognition.inscribed.impl.Inscribed.MOD_ID;
import static net.kognition.inscribed.impl.Inscribed.id;

/**
 * @author TheEnderCore
 */
public class InscribedConfig extends Config {

    public InscribedConfig() {
        super(id("%s_common".formatted(MOD_ID)));
    }


    public ValidatedSet<Identifier> enchantmentAllowlist = ValidatedIdentifier.ofDynamicKey(id("empty"), Registries.ENCHANTMENT, "insc_enchantment_allowlist", (_) -> true).toSet(DEFAULT_ENCHANTS);

    public static Set<Identifier> DEFAULT_ENCHANTS = Stream.of(
            Enchantments.FORTUNE,
            Enchantments.SILK_TOUCH
    ).map(ResourceKey::identifier).collect(Collectors.toSet());

}