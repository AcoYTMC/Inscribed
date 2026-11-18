package net.kognition.inscribed.mixin.enchant;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author TheEnderCore
 */
@Mixin(TagEntry.class)
public interface TagEntryAccessor {
    @Accessor("tag")
    boolean inscribed$isTag();

    @Accessor("id")
    Identifier inscribed$getId();
}
