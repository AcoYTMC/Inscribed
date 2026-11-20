package net.kognition.inscribed.impl.index.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.ArrayList;
import java.util.List;

import static net.kognition.inscribed.impl.Inscribed.id;

/**
 * @author TheEnderCore
 */
public interface ModEnchantments {
    List<ResourceKey<Enchantment>> ENCHANTMENTS = new ArrayList<>();

    ResourceKey<Enchantment> SIPHON = key("siphon");
    ResourceKey<Enchantment> VENDETTA = key("vendetta");
    ResourceKey<Enchantment> HUNTER = key("hunter");
    ResourceKey<Enchantment> WEAVING = key("weaving");
    ResourceKey<Enchantment> LASSO = key("lasso");
    ResourceKey<Enchantment> THRUST = key("thrust");
    ResourceKey<Enchantment> RICOSHATTER = key("ricoshatter");

    private static ResourceKey<Enchantment> key(String name) {
        var key = ResourceKey.create(Registries.ENCHANTMENT, id(name));
        ENCHANTMENTS.add(key);
        return key;
    }
}
