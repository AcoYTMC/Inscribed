package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.component.ClusteredComponent;
import net.kognition.inscribed.impl.component.RuneComponent;
import net.minecraft.core.component.DataComponentType;

import java.util.function.UnaryOperator;

/**
 * @author AcoYT
 */
public interface ModDataComponents {
    DataComponentTypeRegistrant COMPONENTS = new DataComponentTypeRegistrant(Inscribed.MOD_ID);

    DataComponentType<RuneComponent> RUNE = COMPONENTS.register("rune", RuneComponent.CODEC, RuneComponent.STREAM_CODEC);
    DataComponentType<ClusteredComponent> CLUSTERED = COMPONENTS.register("clustered",
            (UnaryOperator<DataComponentType.Builder<ClusteredComponent>>) builder -> builder
                    .persistent(ClusteredComponent.CODEC)
                    .networkSynchronized(ClusteredComponent.STREAM_CODEC)
                    .ignoreSwapAnimation()
    );

    static void init() {}
}
