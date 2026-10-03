package net.kognition.inscribed.impl.client.screen;

import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.block.entity.RuneWeaverMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author AcoYT
 */
public class RuneWeaverScreen extends AbstractContainerScreen<RuneWeaverMenu> {
    private static final Identifier BACKGROUND = Inscribed.id("textures/gui/container/rune_weaver.png");
    private static final Identifier INFO = Inscribed.id("textures/gui/container/sprites/info.png");

    public RuneWeaverScreen(RuneWeaverMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        this.extractInfoTooltip(graphics, mouseX, mouseY);
    }

    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);

        graphics.blit(
                RenderPipelines.GUI_TEXTURED, BACKGROUND,
                this.leftPos, this.topPos,
                0.0F, 0.0F,
                this.imageWidth, this.imageHeight,
                BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT
        );

        graphics.blit(
                RenderPipelines.GUI_TEXTURED, INFO,
                this.leftPos + 5, this.topPos + 5,
                0.0F, 0.0F,
                15, 16,
                15, 16
        );
    }

    private void extractInfoTooltip(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        if (this.isHovering(this.leftPos + 5, this.topPos + 5, 15, 16, mouseX, mouseY)) {
            List<Component> lines = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                lines.add(Component.translatable("tooltip.inscribed.rune_weaver_" + i));
            }

            graphics.setTooltipForNextFrame(
                    this.font,
                    lines, Optional.empty(),
                    mouseX, mouseY
            );
        }
    }
}
