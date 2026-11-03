package net.kognition.inscribed.impl.client.math_type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * @author AcoYT
 */
public record Less(Number value) implements MathDefinition {
    public static final MapCodec<Less> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Number.CODEC.codec().fieldOf("value").forGetter(Less::value)
    ).apply(instance, Less::new));

    public boolean test(int num) {
        return value.inclusive() ? num <= value.value() : num < value.value();
    }

    public MapCodec<? extends MathDefinition> codec() {
        return CODEC;
    }
}
