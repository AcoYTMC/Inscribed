package net.kognition.inscribed.impl;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.kognition.inscribed.impl.client.ModItemTooltips;
import net.kognition.inscribed.impl.client.entity.render.RuneWeaverBlockEntityRenderer;
import net.kognition.inscribed.impl.client.item.select.ClusteredProperty;
import net.kognition.inscribed.impl.client.item.select.RuneData;
import net.kognition.inscribed.impl.client.math_type.ModMathInit;
import net.kognition.inscribed.impl.client.screen.RuneWeaverScreen;
import net.kognition.inscribed.impl.index.ModBlockEntities;
import net.kognition.inscribed.impl.index.ModMenuTypes;
import net.kognition.inscribed.impl.networking.ModNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class InscribedClient implements ClientModInitializer {
    public void onInitializeClient() {
        // Initialization
        MenuScreens.register(ModMenuTypes.RUNE_WEAVER, RuneWeaverScreen::new);

        BlockEntityRenderers.register(ModBlockEntities.RUNE_WEAVER, RuneWeaverBlockEntityRenderer::new);

        SelectItemModelProperties.ID_MAPPER.put(RuneData.ID, RuneData.TYPE);
        SelectItemModelProperties.ID_MAPPER.put(ClusteredProperty.ID, ClusteredProperty.TYPE);

        ModItemTooltips.init();

        ModMathInit.bootstrap();

        // Networking
        ModNetworking.registerClient();

        // Resource Packs
        FabricLoader.getInstance().getModContainer(Inscribed.MOD_ID).ifPresent(container -> {
            ResourceLoader.registerBuiltinPack(
                    Inscribed.id("retextured_redstone"),
                    container,
                    Component.literal("Retextured Redstone"),
                    PackActivationType.DEFAULT_ENABLED
            );
        });
    }
}
