package net.kognition.inscribed.impl.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.kognition.inscribed.impl.block.entity.RuneWeaverBlockEntity;
import net.kognition.inscribed.impl.client.entity.state.RuneWeaverBlockEntityRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

/**
 * @author AcoYT
 */
public class RuneWeaverBlockEntityRenderer implements BlockEntityRenderer<RuneWeaverBlockEntity, RuneWeaverBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public RuneWeaverBlockEntityRenderer(BlockEntityRendererProvider.@NotNull Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    public RuneWeaverBlockEntityRenderState createRenderState() {
        return new RuneWeaverBlockEntityRenderState();
    }

    public void submit(RuneWeaverBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();

        poseStack.translate(0.5F, 1.25F + Math.sin(state.animProgress / 10) / 40.0F, 0.5F);
        poseStack.rotate(Axis.YP.rotationDegrees(state.animProgress * 3));

        state.itemState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }

    public void extractRenderState(RuneWeaverBlockEntity blockEntity, RuneWeaverBlockEntityRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        Minecraft minecraft = Minecraft.getInstance();

        ItemStackRenderState itemState = new ItemStackRenderState();
        itemModelResolver.updateForTopItem(itemState, blockEntity.getRuneStack(), ItemDisplayContext.GROUND, minecraft.level, minecraft.player, 0);
        state.itemState = itemState;

        int age = minecraft.player != null ? minecraft.player.tickCount : 0;
        state.animProgress = age + partialTicks;
    }
}
