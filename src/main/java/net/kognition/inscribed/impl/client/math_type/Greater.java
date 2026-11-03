package net.kognition.inscribed.impl.client.math_type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * @author AcoYT
 */
public record Greater(Number value) implements MathDefinition {
    public static final MapCodec<Greater> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Number.CODEC.codec().fieldOf("value").forGetter(Greater::value)
    ).apply(instance, Greater::new));

    public boolean test(int num) {
        return value.inclusive() ? num >= value.value() : num > value.value();
    }

    public MapCodec<? extends MathDefinition> codec() {
        return CODEC;
    }
}
