package net.kognition.inscribed.mixin.enchant;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Function;

import static net.kognition.inscribed.impl.Inscribed.*;

/**
 * @author TheEnderCore
 */
@Mixin(MappedRegistry.class)
public abstract class MappedRegistryMixin<T> implements WritableRegistry<T> {
    @Shadow
    private boolean frozen;

    @Shadow
    @Final
    private ResourceKey<? extends Registry<T>> key;

    @Inject(method = "freeze", at = @At(value = "HEAD"))
    void injectEnchantAliases(CallbackInfoReturnable<Registry<T>> cir) {
        if (frozen || !key.identifier().equals(Registries.ENCHANTMENT.identifier())) {
            return;
        }
        for (Identifier alias : Inscribed.ENCHANTMENT_ALIASES) {
            if (isDev()) LOGGER.info("Adding alias for: {}", alias);
            addAlias(alias, Enchantments.SILK_TOUCH.identifier());
        }
        Inscribed.ENCHANTMENT_ALIASES.clear();
    }

    @WrapOperation(method = "getOrCreateHolderOrThrow", at = @At(value = "INVOKE", target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;"))
    <K, V> V preventHoldersToAliased(Map<K, V> instance, K key, @NotNull Function<? super K, ? extends V> mappingFunction, Operation<V> original) {
        var rawKey = key;
        if (!frozen && ((ResourceKey<?>) key).isFor(Registries.ENCHANTMENT) && !CONFIG.enchantmentAllowlist.contains(((ResourceKey<?>) key).identifier())) {
            //noinspection unchecked
            rawKey = (K) Enchantments.SILK_TOUCH;
        }
        return original.call(instance, rawKey, mappingFunction);
    }
}
