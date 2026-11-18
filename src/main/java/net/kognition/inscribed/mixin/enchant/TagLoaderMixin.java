package net.kognition.inscribed.mixin.enchant;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import static net.kognition.inscribed.impl.Inscribed.CONFIG;

/**
 * @author TheEnderCore
 */
@Mixin(TagLoader.class)
public class TagLoaderMixin {
    @Shadow
    @Final
    private String directory;

    @ModifyExpressionValue(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/tags/TagFile;entries()Ljava/util/List;"))
    List<TagEntry> addTagEntries(List<TagEntry> original) {
        if (directory.equals("tags/enchantment")) {
            return original.stream().filter(tagEntry -> tagEntry instanceof TagEntryAccessor entry && (entry.inscribed$isTag() || CONFIG.enchantmentAllowlist.contains(entry.inscribed$getId()))).toList();
        }
        return original;
    }
}
