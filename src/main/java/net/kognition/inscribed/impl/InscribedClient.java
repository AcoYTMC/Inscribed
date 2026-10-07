package net.kognition.inscribed.impl;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kognition.inscribed.impl.client.entity.render.RuneWeaverBlockEntityRenderer;
import net.kognition.inscribed.impl.client.screen.RuneWeaverScreen;
import net.kognition.inscribed.impl.index.ModBlockEntities;
import net.kognition.inscribed.impl.index.ModMenuTypes;
import net.kognition.inscribed.impl.networking.ModNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

@Environment(EnvType.CLIENT)
public class InscribedClient implements ClientModInitializer {
    public void onInitializeClient() {
        // Initialization
        MenuScreens.register(ModMenuTypes.RUNE_WEAVER, RuneWeaverScreen::new);

        BlockEntityRenderers.register(ModBlockEntities.RUNE_WEAVER, RuneWeaverBlockEntityRenderer::new);

        // Networking
        ModNetworking.registerClient();
    }
}
