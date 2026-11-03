package net.kognition.inscribed.impl.client.math_type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

/**
 * @author AcoYT
 */
public class MathInit {
    private static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends MathDefinition>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<MathDefinition> CODEC = ID_MAPPER.codec(Identifier.CODEC).dispatch(MathDefinition::codec, c -> c);

    public static void bootstrap() {
        ID_MAPPER.put(Identifier.withDefaultNamespace("between"), Between.CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("greater"), Greater.CODEC);
        ID_MAPPER.put(Identifier.withDefaultNamespace("less"), Less.CODEC);
    }
}
