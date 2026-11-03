package net.kognition.inscribed.impl.client.math_type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * @author AcoYT
 */
public record Between(Number min, Number max) implements MathDefinition {
    public static final MapCodec<Between> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Number.CODEC.codec().fieldOf("min").forGetter(Between::min),
            Number.CODEC.codec().fieldOf("max").forGetter(Between::max)
    ).apply(instance, Between::new));

    public boolean test(int num) {
        boolean greater = min.inclusive() ? num >= min.value() : num > min.value();
        boolean less = max.inclusive() ? num <= max.value() : num < max.value();
        return greater && less;
    }

    public MapCodec<? extends MathDefinition> codec() {
        return CODEC;
    }
}
