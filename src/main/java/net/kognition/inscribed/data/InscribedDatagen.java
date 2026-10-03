package net.kognition.inscribed.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.kognition.inscribed.data.provider.client.ModLanguageProvider;
import net.kognition.inscribed.data.provider.client.ModModelProvider;

public class InscribedDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ModLanguageProvider::new);
        pack.addProvider(ModModelProvider::new);
    }
}
