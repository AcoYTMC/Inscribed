package net.kognition.inscribed.impl;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kognition.inscribed.impl.client.screen.RuneWeaverScreen;
import net.kognition.inscribed.impl.index.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;

@Environment(EnvType.CLIENT)
public class InscribedClient implements ClientModInitializer {
    public void onInitializeClient() {
        MenuScreens.register(ModMenuTypes.RUNE_WEAVER, RuneWeaverScreen::new);
    }
}
