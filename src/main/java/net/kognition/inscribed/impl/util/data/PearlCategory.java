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
            new Pair("gold", 0xFFe0a727),
            new Pair("amber", 0xFF8f6534),
            new Pair("ochre", 0xFF884e2d),
            new Pair("saffron", 0xFFbc9c2e)
    ),
    PRESERVATION(1, "preservation",
            new Pair("cerulean", 0xFF308bb8),
            new Pair("azure", 0xFF227273),
            new Pair("navy", 0xFF2e518d),
            new Pair("turquoise", 0xFF45a7bc)
    ),
    RESTORATION(2, "restoration",
            new Pair("pear", 0xFF4aae70),
            new Pair("jade", 0xFF44be74),
            new Pair("moss", 0xFF628452),
            new Pair("olive", 0xFF8e9e1a)
    ),
    RIGOROUS(3, "rigorous",
            new Pair("ruby", 0xFFd6261c),
            new Pair("brick", 0xFF843b2d),
            new Pair("garnet", 0xFF8f2626),
            new Pair("merlot", 0xFF6e2b26)
    );

    private static final IntFunction<PearlCategory> BY_ID = ByIdMap.continuous(e -> e.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

    public static final Codec<PearlCategory> CODEC = StringRepresentable.fromEnum(PearlCategory::values);
    public static final StreamCodec<ByteBuf, PearlCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, e -> e.id);

    private final int id;
    private final String base;
    private final List<Identifier> textures;
    private final List<Pair> pairs;
    private final List<String> names;

    PearlCategory(int id, String base, Pair... pairs) {
        String[] names = Arrays.stream(pairs).map(Pair::name).toArray(String[]::new);

        this.id = id;
        this.base = base;
        this.textures = fuckassTempNameTooTiredForThis(base, names);
        this.pairs = Arrays.asList(pairs);
        this.names = Arrays.asList(names);
    }

    public List<Identifier> getTextures() {
        return textures;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Pair> getPairs() {
        return pairs;
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

    public Pair getPair(PearlPlacement selected) {
        return switch (selected) {
            case TOP -> pairs.getFirst();
            case RIGHT -> pairs.get(1);
            case LEFT -> pairs.get(2);
            case BOTTOM -> pairs.get(3);
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

    public static List<Identifier> fuckassTempNameTooTiredForThis(String base, String... names) {
        List<Identifier> ids = new ArrayList<>();
        for (String name : names) {
            ids.add(Inscribed.id("textures/gui/sprites/container/rune_weaver/pearl/" + base + "/" + name + ".png"));
        }

        return ids;
    }

    public record Pair(String name, int color) {
        public static final Codec<Pair> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("name").forGetter(Pair::name),
                ExtraCodecs.ARGB_COLOR_CODEC.fieldOf("color").forGetter(Pair::color)
        ).apply(instance, Pair::new));

        public static final StreamCodec<ByteBuf, Pair> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.STRING_UTF8, Pair::name,
                ByteBufCodecs.INT, Pair::color,
                Pair::new
        );
    }
}
