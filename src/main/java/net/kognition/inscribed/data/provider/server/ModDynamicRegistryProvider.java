package net.kognition.inscribed.data.provider.server;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.data.ModBlockTransformers;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 */
public class ModDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public ModDynamicRegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public void configure(HolderLookup.Provider provider, Entries entries) {
        ModBlockTransformers.BUILDER.addEntries(provider, entries);
    }

    public String getName() {
        return Inscribed.MOD_ID + "_registries";
    }
}
