package net.kognition.inscribed.impl;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.kognition.inscribed.impl.index.*;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

public class Inscribed implements ModInitializer {
    public static final String MOD_ID = "inscribed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public void onInitialize() {
        ModBlockEntities.init();
        ModBlocks.init();
        ModCreativeModeTabs.init();
        ModItems.init();
        ModMenuTypes.init();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
