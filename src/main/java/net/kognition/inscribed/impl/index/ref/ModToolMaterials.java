package net.kognition.inscribed.impl.index.ref;

import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ToolMaterial;

/**
 * @author AcoYT
 */
public interface ModToolMaterials {
    ToolMaterial FLINT = new ToolMaterial(
            BlockTags.INCORRECT_FOR_STONE_TOOL,
            131, 4.0F, 1.0F, 5, ModItemTags.HAFTED_CELT_MATERIALS
    );
}
