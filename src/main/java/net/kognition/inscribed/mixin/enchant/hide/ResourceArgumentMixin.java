package net.kognition.inscribed.mixin.enchant.hide;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static net.kognition.inscribed.impl.Inscribed.CONFIG;

/**
 * @author TheEnderCore
 */
@Mixin(ResourceArgument.class)
public class ResourceArgumentMixin {
    @WrapOperation(method = "listSuggestions", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/SharedSuggestionProvider;listSuggestions(Lcom/mojang/brigadier/context/CommandContext;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/commands/SharedSuggestionProvider$ElementSuggestionType;)Ljava/util/concurrent/CompletableFuture;"))
    private <S, E> CompletableFuture<Suggestions> hideEnchants(CommandContext<S> context, SuggestionsBuilder builder, ResourceKey<? extends Registry<E>> registryKey, SharedSuggestionProvider.ElementSuggestionType type, Operation<CompletableFuture<Suggestions>> original) {
        if (registryKey.identifier().equals(Registries.ENCHANTMENT.identifier())) {
            if (context.getSource() instanceof SharedSuggestionProvider provider) {
                var enchants = provider.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                return SharedSuggestionProvider.listSuggestions(context, builder, Registries.ENCHANTMENT, type, enchant -> CONFIG.isEnchantAllowed(Objects.requireNonNull(enchants.getKey(enchant))));
            }
        }
        return original.call(context, builder, registryKey, type);
    }
}
