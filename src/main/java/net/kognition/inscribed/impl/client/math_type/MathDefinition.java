package net.kognition.inscribed.impl.client.math_type;

import com.mojang.serialization.MapCodec;

/**
 * @author AcoYT
 */
public interface MathDefinition {
    MapCodec<? extends MathDefinition> codec();

    boolean test(int num);
}
