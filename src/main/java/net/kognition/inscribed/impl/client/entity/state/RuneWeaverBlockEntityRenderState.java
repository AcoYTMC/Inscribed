package net.kognition.inscribed.impl.client.entity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author AcoYT
 */
public class RuneWeaverBlockEntityRenderState extends BlockEntityRenderState {
    public ItemStack runeStack = ItemStack.EMPTY;
    public Level level = null;
    public ItemOwner owner = null;
    public float bobOffset = RandomSource.create().nextFloat() * (float) Math.PI * 2.0F;
}
