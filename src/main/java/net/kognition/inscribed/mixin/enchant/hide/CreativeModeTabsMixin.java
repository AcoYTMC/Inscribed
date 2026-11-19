package net.kognition.inscribed.mixin.enchant.hide;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.world.item.CreativeModeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.stream.Stream;

import static net.kognition.inscribed.impl.Inscribed.CONFIG;

/**
 * @author TheEnderCore
 */
@Mixin(CreativeModeTabs.class)
public class CreativeModeTabsMixin {
    @ModifyExpressionValue(
            method = {"generateEnchantmentBookTypesOnlyMaxLevel", "generateEnchantmentBookTypesAllLevels"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/core/HolderLookup;listElements()Ljava/util/stream/Stream;")
    )
    private static <T> Stream<Holder.Reference<T>> hideEnchantBooks(Stream<Holder.Reference<T>> original) {
        return original.filter((holder) -> CONFIG.isEnchantAllowed(holder.key().identifier()));
    }
}
