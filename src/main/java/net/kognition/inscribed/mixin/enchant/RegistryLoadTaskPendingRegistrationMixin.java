package net.kognition.inscribed.mixin.enchant;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Decoder;
import net.fabricmc.fabric.impl.resource.conditions.ResourceConditionsImpl;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.kognition.inscribed.impl.Inscribed.CONFIG;

/**
 * @author TheEnderCore
 */
@Mixin(targets = "net.minecraft.resources.RegistryLoadTask$PendingRegistration")
public abstract class RegistryLoadTaskPendingRegistrationMixin {
    @Inject(method = "loadFromResource", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/Resource;openAsReader()Ljava/io/BufferedReader;"), cancellable = true)
    private static <T> void loadFromResource(Decoder<T> elementDecoder, RegistryOps<JsonElement> ops, ResourceKey<T> elementKey, Resource thunk, CallbackInfoReturnable<Either<T, Exception>> cir) {
        if (elementKey.isFor(Registries.ENCHANTMENT) && !CONFIG.enchantmentAllowlist.contains(elementKey.identifier())) {
            Inscribed.ENCHANTMENT_ALIASES.add(elementKey.identifier());
            // Using fabric internals so we don't have to manually prevent the game from breaking
            cir.setReturnValue(Either.right(ResourceConditionsImpl.DISABLED_RESOURCE_EXCEPTION));
        }
    }
}
