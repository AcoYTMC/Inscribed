package net.kognition.inscribed.impl.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.ModItems;
import net.kognition.inscribed.impl.index.ModSounds;
import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.kognition.inscribed.impl.networking.serverbound.ApplyRunePayload;
import net.kognition.inscribed.impl.networking.serverbound.SetSelectedPayload;
import net.kognition.inscribed.impl.util.data.PearlCategory;
import net.kognition.inscribed.impl.util.data.PearlPlacement;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.player.LocalPlayer;
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
    private static final Identifier PEARL_OUTLINE = Inscribed.id("textures/gui/sprites/container/rune_weaver/pearl/default_outline.png");

    private static final Identifier PEARL_SMALL = Inscribed.id("textures/gui/sprites/container/rune_weaver/pearl_small.png");
    private static final Identifier PEARL_SMALL_OUTLINE = Inscribed.id("textures/gui/sprites/container/rune_weaver/pearl_small_outline.png");

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

    public void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        //
    }

    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        LocalPlayer player = minecraft.player;

        double mouseX = event.x();
        double mouseY = event.y();

        if (getRuneStack().isPresent()) {
            for (PearlPlacement selected : PearlPlacement.values()) {
                if (selected.isHovered(this.leftPos, this.topPos, mouseX, mouseY)) {
                    if (selected == PearlPlacement.BIG) {
                        if (menu.selected != PearlPlacement.NONE) {
                            ClientPlayNetworking.send(new ApplyRunePayload());
                            ClientPlayNetworking.send(new SetSelectedPayload(PearlPlacement.NONE));
                            if (player != null) player.playSound(ModSounds.RUNE_WEAVER_CONFIRM, 1.0F, 1.0F);
                        }
                    } else {
                        if (this.menu.selected == selected) {
                            ClientPlayNetworking.send(new SetSelectedPayload(PearlPlacement.NONE));
                            this.menu.selected = PearlPlacement.NONE;
                            if (player != null) player.playSound(ModSounds.RUNE_WEAVER_DESELECT, 1.0F, 1.0F);
                        } else {
                            ClientPlayNetworking.send(new SetSelectedPayload(selected));
                            this.menu.selected = selected;
                            if (player != null) player.playSound(ModSounds.RUNE_WEAVER_SELECT, 1.0F, 1.0F);
                        }
                    }
                }
            }
        }

        return super.mouseClicked(event, doubleClick);
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

        if (menu.type != null && !pearl.isEmpty()) {
            PearlCategory category = menu.type.category();
            Identifier bigTexture = category.getTexture(menu.selected);
            if (bigTexture == null) bigTexture = PEARL;

            if (bigTexture != null) {
                if (PearlPlacement.BIG.isHovered(this.leftPos, this.topPos, mouseX, mouseY)) {
                    graphics.blit(
                            RenderPipelines.GUI_TEXTURED, PEARL_OUTLINE,
                            this.leftPos + PearlPlacement.BIG.getLocation()[0], this.topPos + PearlPlacement.BIG.getLocation()[1],
                            0.0F, 0.0F,
                            32, 32,
                            32, 32
                    );
                }

                graphics.blit(
                        RenderPipelines.GUI_TEXTURED, bigTexture,
                        this.leftPos + PearlPlacement.BIG.getLocation()[0], this.topPos + PearlPlacement.BIG.getLocation()[1],
                        0.0F, 0.0F,
                        32, 32,
                        32, 32
                );

                for (PearlPlacement placement : PearlPlacement.values()) {
                    if (!category.hasPlacement(placement) || placement == PearlPlacement.BIG || placement == PearlPlacement.NONE) continue;

                    int[] location = placement.getLocation();
                    int[] size = placement.getSize();

                    if (menu.selected == placement) {
                        graphics.blit(
                                RenderPipelines.GUI_TEXTURED, PEARL_SMALL_OUTLINE,
                                this.leftPos + location[0], this.topPos + location[1],
                                0.0F, 0.0F,
                                size[0], size[1],
                                size[0], size[1]
                        );
                    }

                    graphics.blit(
                            RenderPipelines.GUI_TEXTURED, PEARL_SMALL,
                            this.leftPos + location[0], this.topPos + location[1],
                            0.0F, 0.0F,
                            size[0], size[1],
                            size[0], size[1]
                    );
                }
            }
        }
    }
}
