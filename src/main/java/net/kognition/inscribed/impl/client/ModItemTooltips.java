package net.kognition.inscribed.impl.client;

import net.acoyt.acornlib.api.event.BetterItemTooltipEvent;
import net.kognition.inscribed.impl.component.ClusteredComponent;
import net.kognition.inscribed.impl.index.ModDataComponents;
import net.minecraft.network.chat.Component;

/**
 * @author AcoYT
 */
public class ModItemTooltips {
    public static void init() {
        BetterItemTooltipEvent.EVENT.register((stack, context, flag, lines) -> {
            if (stack.has(ModDataComponents.CLUSTERED)) {
                ClusteredComponent component = ClusteredComponent.get(stack);
                lines.accept(Component.literal("%s/%s".formatted(component.charges(), component.maxCharges())).withColor(0xFFc798ec));
            }
        });
    }
}
