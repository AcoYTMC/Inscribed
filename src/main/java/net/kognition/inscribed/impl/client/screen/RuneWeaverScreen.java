package net.kognition.inscribed.impl.client.screen;

import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.ModItems;
import net.kognition.inscribed.impl.inventory.RuneWeaverMenu;
import net.kognition.inscribed.impl.util.ModUtil;
import net.kognition.inscribed.impl.util.data.PearlType;
import net.kognition.inscribed.impl.util.data.PearlTypeReloadListener;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author AcoYT
 */
public class RuneWeaverScreen extends AbstractContainerScreen<RuneWeaverMenu> {
    private static final Identifier BACKGROUND = Inscribed.id("textures/gui/sprites/container/rune_weaver/rune_weaver.png");
    private static final Identifier INFO = Inscribed.id("textures/gui/sprites/container/rune_weaver/info.png");

    private static final Identifier PEARL = Inscribed.id("textures/gui/sprites/container/rune_weaver/pearl/default.png");
    private static final Identifier PEARL_SMALL = Inscribed.id("textures/gui/sprites/container/rune_weaver/pearl_small.png");

    private final CyclingSlotBackground filterIcon = new CyclingSlotBackground(2);

    public RuneWeaverScreen(RuneWeaverMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    public void containerTick() {
        super.containerTick();

        Optional<ItemStack> runeItem = getRuneStack();
        this.filterIcon.tick(runeItem.map(_ -> SmithingTemplateItem.createNetheriteUpgradeIconList()).orElse(List.of()));
    }

    private Optional<ItemStack> getRuneStack() {
        ItemStack stack = this.menu.runeInventory.getItem(0);
        return !stack.is(ModItems.RUNE) ? Optional.empty() : Optional.of(stack);
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        this.extractInfoTooltip(graphics, mouseX, mouseY);
        this.extractPearls(graphics, mouseX, mouseY, a);
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

        this.filterIcon.extractRenderState(this.menu, graphics, a, this.leftPos, this.topPos);
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

    private void extractPearls(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        ItemStack pearl = this.menu.pearlSlot.getItem();
        ItemStack filter = this.menu.filterSlot.getItem();
        List<PearlType> pearlTypes = PearlTypeReloadListener.getFromVariables(filter);

        if (!pearl.isEmpty()) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED, PEARL,
                    this.leftPos + ModUtil.BIG[0], this.topPos + ModUtil.BIG[1],
                    0.0F, 0.0F,
                    32, 32,
                    32, 32
            );
        }

        if (!pearlTypes.isEmpty() && !pearl.isEmpty()) {
            int[][] indexed = new int[][]{ModUtil.TOP, ModUtil.RIGHT, ModUtil.BOTTOM, ModUtil.LEFT};

            PearlType type = pearlTypes.getFirst();

            for (int i = 0; i < type.pearls().size(); i++) {
                graphics.blit(
                        RenderPipelines.GUI_TEXTURED, PEARL_SMALL,
                        this.leftPos + indexed[i][0], this.topPos + indexed[i][1],
                        0.0F, 0.0F,
                        16, 16,
                        16, 16
                );
            }
        }
    }
}
