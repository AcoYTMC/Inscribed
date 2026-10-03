package net.kognition.inscribed.data.provider.client;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.kognition.inscribed.impl.index.ModBlocks;
import net.kognition.inscribed.impl.index.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

/**
 * @author AcoYT
 */
public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockModelGenerators generators) {
        generators.createNonTemplateModelBlock(ModBlocks.RUNE_WEAVER.get());
    }

    public void generateItemModels(ItemModelGenerators generators) {
        generators.generateFlatItem(ModItems.PEARL, ModelTemplates.FLAT_ITEM);
        generators.generateFlatItem(ModItems.RUNE, ModelTemplates.FLAT_ITEM);
    }
}
