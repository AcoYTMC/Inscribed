package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.component.RuneComponent;
import net.minecraft.core.component.DataComponentType;

/**
 * @author AcoYT
 */
public interface ModDataComponents {
    DataComponentTypeRegistrant COMPONENTS = new DataComponentTypeRegistrant(Inscribed.MOD_ID);

    DataComponentType<RuneComponent> RUNE = COMPONENTS.register("rune", RuneComponent.CODEC, RuneComponent.STREAM_CODEC);

    static void init() {}
}
