package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.CreativeModeTabRegistrant;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * @author AcoYT
 */
public interface ModCreativeModeTabs {
    CreativeModeTabRegistrant TABS = new CreativeModeTabRegistrant(Inscribed.MOD_ID);

    ResourceKey<CreativeModeTab> INSCRIBED_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Inscribed.id(Inscribed.MOD_ID));
    CreativeModeTab INSCRIBED = TABS.register(Inscribed.MOD_ID, FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup.inscribed"))
            .icon(() -> new ItemStack(ModItems.RUNE))
            .build());

    static void init() {
        CreativeModeTabEvents.modifyOutputEvent(INSCRIBED_KEY).register(ModCreativeModeTabs::addEntries);
    }

    private static void addEntries(FabricCreativeModeTabOutput output) {
        output.accept(ModItems.PEARL);
        output.accept(ModItems.RUNE);

        output.accept(ModBlocks.RUNE_WEAVER.asItem());
    }
}
