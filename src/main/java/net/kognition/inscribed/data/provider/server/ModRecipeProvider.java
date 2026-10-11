package net.kognition.inscribed.data.provider.server;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.kognition.inscribed.impl.index.ModItems;
import net.kognition.inscribed.impl.index.tag.ModItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public RecipeProvider createRecipeProvider(HolderLookup.Provider provider, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new Provider(provider, recipes, advancements);
    }

    public static class Provider extends RecipeProvider {
        private final HolderLookup.Provider provider;

        public Provider(HolderLookup.Provider provider, BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
            super(recipeOutput, advancementOutput);
            this.provider = provider;
        }

        public void buildRecipes() {
            shaped(RecipeCategory.TOOLS, ModItems.HAFTED_CELT)
                    .define('#', Items.STICK)
                    .define('X', ModItemTags.HAFTED_CELT_MATERIALS)
                    .pattern("X#X")
                    .pattern("X# ")
                    .pattern(" # ")
                    .unlockedBy("has_flint", this.has(Items.FLINT))
                    .save(output, "hafted_celt");

            shaped(RecipeCategory.TOOLS, ModItems.HAFTED_CELT)
                    .define('#', Items.STICK)
                    .define('X', ModItemTags.HAFTED_CELT_MATERIALS)
                    .pattern("X#X")
                    .pattern(" #X")
                    .pattern(" # ")
                    .unlockedBy("has_flint", this.has(Items.FLINT))
                    .save(output, "hafted_celt_inverted");
        }
    }
}
