package net.kognition.inscribed.impl.client.math_type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

/**
 * @author AcoYT
 */
public class ModMathInit {
    private static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends MathDefinition>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<MathDefinition> CODEC = ID_MAPPER.codec(Identifier.CODEC).dispatch(MathDefinition::codec, c -> c);

    public static void bootstrap() {
        ID_MAPPER.put(Inscribed.id("between"), Between.CODEC);
        ID_MAPPER.put(Inscribed.id("greater"), Greater.CODEC);
        ID_MAPPER.put(Inscribed.id("less"), Less.CODEC);
    }
}
