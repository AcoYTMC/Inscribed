package net.kognition.inscribed.impl.util.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

/**
 * @author AcoYT
 */
public enum PearlCategory implements StringRepresentable {
    DEXTERITY(0, "dexterity",
            new Type("gold", 0xFFe0a727),
            new Type("amber", 0xFF8f6534),
            new Type("ochre", 0xFF884e2d),
            new Type("saffron", 0xFFbc9c2e)
    ),
    PRESERVATION(1, "preservation",
            new Type("cerulean", 0xFF308bb8),
            new Type("azure", 0xFF227273),
            new Type("navy", 0xFF2e518d),
            new Type("turquoise", 0xFF45a7bc)
    ),
    RESTORATION(2, "restoration",
            new Type("pear", 0xFF4aae70),
            new Type("jade", 0xFF44be74),
            new Type("moss", 0xFF628452),
            new Type("olive", 0xFF8e9e1a)
    ),
    RIGOROUS(3, "rigorous",
            new Type("ruby", 0xFFd6261c),
            new Type("brick", 0xFF843b2d),
            new Type("garnet", 0xFF8f2626),
            new Type("merlot", 0xFF6e2b26)
    );

    private static final IntFunction<PearlCategory> BY_ID = ByIdMap.continuous(e -> e.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

    public static final Codec<PearlCategory> CODEC = StringRepresentable.fromEnum(PearlCategory::values);
    public static final StreamCodec<ByteBuf, PearlCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, e -> e.id);

    private final int id;
    private final String base;
    private final List<Identifier> textures;
    private final List<Type> types;
    private final List<String> names;

    PearlCategory(int id, String base, Type... types) {
        String[] names = Arrays.stream(types).map(Type::name).toArray(String[]::new);

        this.id = id;
        this.base = base;
        this.textures = getTexturesFromBase(base, names);
        this.types = Arrays.asList(types);
        this.names = Arrays.asList(names);
    }

    public List<Identifier> getTextures() {
        return textures;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Type> getPairs() {
        return types;
    }

    /**
     * Gets the texture based on the selected pearl
     */
    public Identifier getTexture(PearlPlacement selected) {
        return switch (selected) {
            case TOP -> textures.getFirst();
            case RIGHT -> textures.get(1);
            case LEFT -> textures.get(2);
            case BOTTOM -> textures.get(3);
            case null, default -> null;
        };
    }

    public String getName(PearlPlacement selected) {
        return switch (selected) {
            case TOP -> names.getFirst();
            case RIGHT -> names.get(1);
            case LEFT -> names.get(2);
            case BOTTOM -> names.get(3);
            case null, default -> null;
        };
    }

    public Type getPair(PearlPlacement selected) {
        return switch (selected) {
            case TOP -> types.getFirst();
            case RIGHT -> types.get(1);
            case LEFT -> types.get(2);
            case BOTTOM -> types.get(3);
            case null, default -> null;
        };
    }

    public boolean hasPlacement(PearlPlacement placement) {
        return switch (placement) {
            case TOP -> !textures.isEmpty();
            case RIGHT -> textures.size() > 1;
            case LEFT -> textures.size() > 2;
            case BOTTOM -> textures.size() > 3;
            case BIG -> true;
            case null, default -> false;
        };
    }

    public String getSerializedName() {
        return base;
    }

    public static List<Identifier> getTexturesFromBase(String base, String... names) {
        List<Identifier> ids = new ArrayList<>();
        for (String name : names) {
            ids.add(Inscribed.id("textures/gui/sprites/container/rune_weaver/pearl/" + base + "/" + name + ".png"));
        }

        return ids;
    }

    public record Type(String name, int color) {
        public static final Codec<Type> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("name").forGetter(Type::name),
                ExtraCodecs.ARGB_COLOR_CODEC.fieldOf("color").forGetter(Type::color)
        ).apply(instance, Type::new));

        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, Type::name,
                ByteBufCodecs.INT, Type::color,
                Type::new
        );
    }
}
