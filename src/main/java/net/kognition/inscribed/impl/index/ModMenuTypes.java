package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.MenuTypeRegistrant;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.inventories.RuneWeaverMenu;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

/**
 * @author AcoYT
 */
public interface ModMenuTypes {
    MenuTypeRegistrant TYPES = new MenuTypeRegistrant(Inscribed.MOD_ID);

    MenuType<RuneWeaverMenu> RUNE_WEAVER = TYPES.register("rune_weaver",
            new MenuType<>(RuneWeaverMenu::new, FeatureFlagSet.of()));

    static void init() {}
}
