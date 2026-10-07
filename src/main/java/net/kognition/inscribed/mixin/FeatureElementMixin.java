package net.kognition.inscribed.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author AcoYT
 */
@Mixin(FeatureElement.class)
public interface FeatureElementMixin {
    @WrapMethod(method = "isEnabled")
    private boolean inscribed$disableStoneTools(FeatureFlagSet enabledFeatures, Operation<Boolean> original) {
        if ((Object)this instanceof Item item) {
            if (item.getDefaultInstance().is(ModItemTags.REMOVED)) {
                return false;
            }
        }

        return original.call(enabledFeatures);
    }
}
