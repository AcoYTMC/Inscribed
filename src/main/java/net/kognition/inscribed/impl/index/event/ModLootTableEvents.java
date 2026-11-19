package net.kognition.inscribed.impl.index.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.component.DataComponents;

/**
 * @author TheEnderCore
 */
public interface ModLootTableEvents {
    static void init() {
        LootTableEvents.MODIFY_DROPS.register((_, _, drops) -> {
            for (int idx = 0; idx < drops.size(); idx++) {
                var stack = drops.get(idx);
                var enchantments = stack.get(DataComponents.STORED_ENCHANTMENTS);
                if (enchantments != null && enchantments.isEmpty()) {
                    drops.remove(stack);
                }
            }
        });
    }
}
