package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.block.entity.RuneWeaverBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * @author AcoYT
 */
public interface ModBlockEntities {
    BlockEntityTypeRegistrant BLOCK_ENTITIES = new BlockEntityTypeRegistrant(Inscribed.MOD_ID);

    BlockEntityType<RuneWeaverBlockEntity> RUNE_WEAVER = BLOCK_ENTITIES.register("rune_weaver",
            FabricBlockEntityTypeBuilder.create(RuneWeaverBlockEntity::new, ModBlocks.RUNE_WEAVER.get()));

    static void init() {}
}
