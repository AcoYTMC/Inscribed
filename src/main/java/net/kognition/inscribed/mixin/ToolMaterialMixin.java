package net.kognition.inscribed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {
    @ModifyVariable(
            method = "<init>",
            at = @At("HEAD"),
            argsOnly = true,
            name = "speed"
    )
    private static float inscribed$makeWoodenToolsSlightlyFaster(float speed, @Local(argsOnly = true, name = "repairItems") TagKey<Item> repairItems) {
        return repairItems == ItemTags.WOODEN_TOOL_MATERIALS ? speed + 1.0F : speed;
    }

    @ModifyVariable(
            method = "<init>",
            at = @At("HEAD"),
            argsOnly = true,
            name = "incorrectBlocksForDrops"
    )
    private static TagKey<Block> inscribed$makeGoldToolsUseful(TagKey<Block> incorrectBlocksForDrops) {
        return incorrectBlocksForDrops == BlockTags.INCORRECT_FOR_GOLD_TOOL ? BlockTags.INCORRECT_FOR_STONE_TOOL : incorrectBlocksForDrops;
    }
}
