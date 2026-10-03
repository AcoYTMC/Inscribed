package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.block.WrappedBlock;
import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.block.RuneWeaverBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * @author AcoYT
 */
public interface ModBlocks {
    BlockRegistrant BLOCKS = new BlockRegistrant(Inscribed.MOD_ID);

    WrappedBlock<Block> RUNE_WEAVER = BLOCKS.registerWithItem("rune_weaver",
            RuneWeaverBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE));

    static void init() {}
}
