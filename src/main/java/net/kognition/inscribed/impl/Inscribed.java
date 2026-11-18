package net.kognition.inscribed.impl;

import com.mojang.logging.LogUtils;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.kognition.inscribed.impl.config.InscribedConfig;
import net.kognition.inscribed.impl.index.*;
import net.kognition.inscribed.impl.networking.ModNetworking;
import net.kognition.inscribed.impl.util.data.PearlType;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class Inscribed implements ModInitializer {
    public static final String MOD_ID = "inscribed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final InscribedConfig CONFIG = ConfigApiJava.registerAndLoadConfig(InscribedConfig::new);

    public static final ResourceKey<Registry<PearlType>> PEARL_DATA_KEY = ResourceKey.createRegistryKey(id("pearl_types"));

    public static final Set<Identifier> ENCHANTMENT_ALIASES = new HashSet<>();

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
        DynamicRegistries.registerSynced(PEARL_DATA_KEY, PearlType.DIRECT_CODEC);

        ItemComponentTooltipProviderRegistry.addBefore(DataComponents.TROPICAL_FISH_PATTERN, ModDataComponents.RUNE);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static boolean isDev() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
