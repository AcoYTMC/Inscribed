package net.kognition.inscribed.impl.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.kognition.inscribed.impl.block.entity.RuneWeaverBlockEntity;
import net.kognition.inscribed.impl.client.entity.state.RuneWeaverBlockEntityRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
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

        LivingEntity entity = state.owner.asLivingEntity();
        float tickProgress = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
        int ageInTicks = entity != null ? entity.tickCount : 0;

        poseStack.translate(0.5F, 1.25F + Math.sin((ageInTicks + tickProgress) / 10) / 40.0F, 0.5F);
        poseStack.rotate(Axis.YP.rotationDegrees((ageInTicks + tickProgress) * 3));

        ItemStackRenderState renderState = new ItemStackRenderState();
        itemModelResolver.updateForTopItem(renderState, state.runeStack, ItemDisplayContext.GROUND, state.level, state.owner, 0);

        renderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }

    public void extractRenderState(RuneWeaverBlockEntity blockEntity, RuneWeaverBlockEntityRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderState.extractBase(blockEntity, state, breakProgress);
        state.runeStack = blockEntity.getRuneStack();
        state.level = Minecraft.getInstance().level;
        state.owner = Minecraft.getInstance().player;
    }
}
