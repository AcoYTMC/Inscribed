package net.kognition.inscribed.impl.block;

import net.kognition.inscribed.impl.block.entity.RuneWeaverBlockEntity;
import net.kognition.inscribed.impl.index.ModSounds;
import net.kognition.inscribed.impl.index.ModStats;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

/**
 * @author AcoYT
 */
public class RuneWeaverBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Shapes.or(
            box(2, 0, 2, 14, 4, 14),
            box(0, 4, 0, 16, 16, 16)
    );

    public RuneWeaverBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof RuneWeaverBlockEntity weaverBlock && weaverBlock.getActiveEntity() == null) {
            if (level.isClientSide()) {
                player.playSound(ModSounds.RUNE_WEAVER_OPEN, 1.0F, 1.0F);
            } else {
                player.openMenu(getMenuProvider(state, level, pos));
                player.awardStat(ModStats.INTERACT_WITH_RUNE_WEAVER);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new RuneWeaverBlockEntity(worldPosition, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level ignoredLevel, BlockState ignoredState, BlockEntityType<T> ignoredType) {
        return (level, pos, state, entity) -> {
            if (entity instanceof RuneWeaverBlockEntity weaverBlock) {
                weaverBlock.tick(level, pos, state);
            }
        };
    }
}
