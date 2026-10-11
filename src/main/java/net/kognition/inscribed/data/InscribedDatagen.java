package net.kognition.inscribed.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.kognition.inscribed.data.provider.client.ModLanguageProvider;
import net.kognition.inscribed.data.provider.client.ModModelProvider;
import net.kognition.inscribed.data.provider.client.ModSoundsProvider;
import net.kognition.inscribed.data.provider.server.ModDynamicRegistryProvider;
import net.kognition.inscribed.data.provider.server.ModPearlTypeProvider;
import net.kognition.inscribed.data.provider.server.ModRecipeProvider;
import net.kognition.inscribed.data.provider.server.tags.ModBlockTagsProvider;
import net.kognition.inscribed.data.provider.server.tags.ModItemTagsProvider;
import net.kognition.inscribed.impl.index.data.ModBlockTransformers;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class InscribedDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        // Client
        pack.addProvider(ModLanguageProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModSoundsProvider::new);

        // Server
        pack.addProvider(ModBlockTagsProvider::new);
        pack.addProvider(ModItemTagsProvider::new);

        pack.addProvider(ModRecipeProvider::new);

        pack.addProvider(ModPearlTypeProvider::new);

        pack.addProvider(ModDynamicRegistryProvider::new);
    }

    public void buildRegistry(RegistrySetBuilder builder) {
        builder.add(Registries.BLOCK_TRANSFORMER, ModBlockTransformers.BUILDER::bootstrap);
    }
}
