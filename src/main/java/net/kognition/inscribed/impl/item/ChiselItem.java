package net.kognition.inscribed.impl.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * @author AcoYT
 */
public class ChiselItem extends Item {
    public ChiselItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (level instanceof ServerLevel serverLevel && !stack.nextDamageWillBreak() && player != null) {
            SingleRecipeInput input = new SingleRecipeInput(state.getBlock().asItem().getDefaultInstance());
            Optional<RecipeHolder<StonecutterRecipe>> optionalHolder = serverLevel.recipeAccess().getRecipeFor(
                    RecipeType.STONECUTTING,
                    input,
                    serverLevel
            );

            if (optionalHolder.isPresent()) {
                StonecutterRecipe recipe = optionalHolder.get().value();
                ItemStack itemStack = recipe.assemble(input);

                if (itemStack.getItem() instanceof BlockItem blockItem) {
                    Block block = blockItem.getBlock();
                    level.setBlock(pos, block.withPropertiesOf(state), Block.UPDATE_ALL);
                    stack.hurtWithoutBreaking(1, player);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.useOn(context);
    }
}
