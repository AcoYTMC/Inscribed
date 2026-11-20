package net.kognition.inscribed.impl.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

/**
 * @author TheEnderCore
 */
public class SpikeProjectileRenderState extends EntityRenderState {
    public float yRot;
    public float xRot;
    public final ItemStackRenderState item = new ItemStackRenderState();
}
