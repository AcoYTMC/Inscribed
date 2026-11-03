package net.kognition.inscribed.mixin.client;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.kognition.inscribed.api.item.ExtraBarItem;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author AcoYT
 */
@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    @Shadow public abstract void fill(RenderPipeline pipeline, int x0, int y0, int x1, int y1, int col);

    @Inject(method = "itemBar", at = @At("TAIL"))
    private void inscribed$renderExtraBar(ItemStack itemStack, int x, int y, CallbackInfo ci) {
        if (itemStack.getItem() instanceof ExtraBarItem barItem && barItem.isExtraBarVisible(itemStack)) {
            int left = x + 2;
            int top = y + 15;
            this.fill(
                    RenderPipelines.GUI,
                    left, top,
                    left + 13, top + 2,
                    -16777216
            );

            this.fill(
                    RenderPipelines.GUI,
                    left, top,
                    left + barItem.getExtraBarWidth(itemStack), top + 1,
                    ARGB.opaque(barItem.getExtraBarColor(itemStack))
            );
        }
    }
}
