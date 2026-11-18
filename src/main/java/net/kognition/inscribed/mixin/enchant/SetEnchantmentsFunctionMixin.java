package net.kognition.inscribed.mixin.enchant;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.MapCodec;
import net.kognition.inscribed.impl.util.codecs.DeduplicationMapCodec;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

/**
 * @author TheEnderCore
 */
@Mixin(SetEnchantmentsFunction.class)
public class SetEnchantmentsFunctionMixin {
    @SuppressWarnings({"unchecked", "rawtypes"})
    @ModifyExpressionValue(method = "lambda$static$0", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/UnboundedMapCodec;optionalFieldOf(Ljava/lang/String;Ljava/lang/Object;)Lcom/mojang/serialization/MapCodec;"))
    private static MapCodec modifyCodecType(MapCodec original) {
        return new DeduplicationMapCodec(Enchantment.CODEC, ContextIntProviders.CODEC).optionalFieldOf("enchantments", Map.of());
    }
}
