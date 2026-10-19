package net.kognition.inscribed.impl.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Optional;

/**
 * @author AcoYT
 */
public class ChiselItem extends Item {
    public ChiselItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (level instanceof ServerLevel serverLevel) {
            Optional<RecipeHolder<StonecutterRecipe>> optionalHolder = serverLevel.recipeAccess().getRecipeFor(
                    RecipeType.STONECUTTING,
                    new SingleRecipeInput(state.getBlock().asItem().getDefaultInstance()),
                    serverLevel
            );

            if (optionalHolder.isPresent()) {
                StonecutterRecipe recipe = optionalHolder.get().value();
                ItemStack stack = recipe.result.create();

                if (stack.getItem() instanceof BlockItem blockItem) {
                    Block block = blockItem.getBlock();
                    level.setBlock(pos, copyFrom(state, block), Block.UPDATE_ALL);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.useOn(context);
    }

    private static BlockState copyFrom(BlockState from, Block to) {
        BlockState state = to.defaultBlockState();

        for (Property<?> property : from.getProperties()) {
            state = BlockBehaviour.BlockStateBase.copyProperty(from, state, property);
        }

        return state;
    }
}
