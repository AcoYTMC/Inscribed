package net.kognition.inscribed.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.kognition.inscribed.data.provider.client.ModLanguageProvider;
import net.kognition.inscribed.data.provider.client.ModModelProvider;
import net.kognition.inscribed.data.provider.client.ModSoundsProvider;
import net.kognition.inscribed.data.provider.server.ModPearlTypeProvider;
import net.kognition.inscribed.data.provider.server.tags.ModItemTagsProvider;

public class InscribedDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        // Client
        pack.addProvider(ModLanguageProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModSoundsProvider::new);

        // Server
        pack.addProvider(ModItemTagsProvider::new);

        pack.addProvider(ModPearlTypeProvider::new);
    }
}
