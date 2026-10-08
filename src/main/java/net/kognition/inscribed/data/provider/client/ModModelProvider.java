package net.kognition.inscribed.data.provider.client;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.client.item.select.RuneData;
import net.kognition.inscribed.impl.index.ModBlocks;
import net.kognition.inscribed.impl.index.ModItems;
import net.kognition.inscribed.impl.util.data.PearlCategory;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

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
        registerRune(generators, ModItems.RUNE);
    }

    public static void registerRune(ItemModelGenerators generators, Item item) {
        List<SelectItemModel.SwitchCase<RuneData.Data>> switchCases = new ArrayList<>();
        Identifier fallback = ModelTemplates.FLAT_ITEM.create(item, TextureMapping.layer0(item), generators.modelOutput);

        for (PearlCategory category : PearlCategory.values()) {
            for (String name : category.getNames()) {
                Identifier varId = Inscribed.id("item/rune_%s_%s".formatted(category.getSerializedName(), name));
                ModelTemplates.FLAT_ITEM.create(varId, TextureMapping.layer0(new Material(varId)), generators.modelOutput);

                switchCases.add(new SelectItemModel.SwitchCase<>(
                        List.of(new RuneData.Data(category, name)),
                        ItemModelUtils.plainModel(varId)
                ));
            }
        }

        generators.itemModelOutput.accept(item, ItemModelUtils.select(
                new RuneData(),
                ItemModelUtils.plainModel(fallback), switchCases
        ));
    }
}
