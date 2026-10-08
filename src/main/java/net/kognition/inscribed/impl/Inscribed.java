package net.kognition.inscribed.impl;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.kognition.inscribed.impl.index.*;
import net.kognition.inscribed.impl.networking.ModNetworking;
import net.kognition.inscribed.impl.util.data.PearlType;
import net.kognition.inscribed.impl.util.data.PearlTypeReloadListener;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;

public class Inscribed implements ModInitializer {
    public static final String MOD_ID = "inscribed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<Registry<PearlType>> PEARL_DATA_KEY = ResourceKey.createRegistryKey(id(PearlTypeReloadListener.PATH));

    public void onInitialize() {
        // Initialization
        ModBlockEntities.init();
        ModBlocks.init();
        ModCreativeModeTabs.init();
        ModDataComponents.init();
        ModItems.init();
        ModMenuTypes.init();
        ModSounds.init();
        ModStats.init();

        // Networking
        ModNetworking.registerCommon();

        // Reload Listeners
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(id(PearlTypeReloadListener.PATH), new PearlTypeReloadListener());

        DynamicRegistries.registerSynced(PEARL_DATA_KEY, PearlType.DIRECT_CODEC);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
