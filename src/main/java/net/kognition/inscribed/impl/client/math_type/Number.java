package net.kognition.inscribed.impl.client.math_type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * @author AcoYT
 */
public record Number(int value, boolean inclusive) {
    public static final MapCodec<Number> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("value").forGetter(Number::value),
            Codec.BOOL.optionalFieldOf("inclusive", false).forGetter(Number::inclusive)
    ).apply(instance, Number::new));

    public Number(int value) {
        this(value, false);
    }
}
