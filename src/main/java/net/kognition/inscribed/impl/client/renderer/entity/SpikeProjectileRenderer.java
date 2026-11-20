package net.kognition.inscribed.impl.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.kognition.inscribed.impl.client.renderer.entity.state.SpikeProjectileRenderState;
import net.kognition.inscribed.impl.entity.SpikeProjectile;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;

/**
 * @author TheEnderCore
 */
public class SpikeProjectileRenderer extends EntityRenderer<SpikeProjectile, SpikeProjectileRenderState> {
    private final ItemModelResolver itemModelResolver;

    public SpikeProjectileRenderer(final EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
    }

    public void submit(
            final SpikeProjectileRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera
    ) {
        poseStack.pushPose();
        var scale = 2f;
        poseStack.scale(scale, scale, scale);
        poseStack.rotateDegrees(Axis.YP, state.yRot - 90f);
        poseStack.rotateDegrees(Axis.ZP, state.xRot + 90f);
        poseStack.translate(0f, -0.1f, 0f);
        state.item.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    public SpikeProjectileRenderState createRenderState() {
        return new SpikeProjectileRenderState();
    }

    public void extractRenderState(final SpikeProjectile entity, final SpikeProjectileRenderState state, final float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.xRot = entity.getXRot(partialTicks);
        state.yRot = entity.getYRot(partialTicks);
        this.itemModelResolver.updateForNonLiving(state.item, entity.getRenderStack(), ItemDisplayContext.GROUND, entity);
    }
}
