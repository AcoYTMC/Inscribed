package net.kognition.inscribed.impl.util.data;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

/**
 * @author AcoYT
 */
public enum PearlCategory implements StringRepresentable {
    DEXTERITY(0, "dexterity", "gold", "amber", "ochre", "saffron"),
    PRESERVATION(1, "preservation", "cerulean", "azure", "navy", "turquoise"),
    RESTORATION(2, "restoration", "pear", "jade", "moss", "olive"),
    RIGOROUS(3, "rigorous", "ruby", "brick", "garnet", "merlot");

    private static final IntFunction<PearlCategory> BY_ID = ByIdMap.continuous(e -> e.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

    public static final Codec<PearlCategory> CODEC = StringRepresentable.fromEnum(PearlCategory::values);
    public static final StreamCodec<ByteBuf, PearlCategory> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, e -> e.id);

    private final int id;
    private final String base;
    private final List<Identifier> textures;
    private final List<String> names;

    PearlCategory(int id, String base, String... names) {
        this.id = id;
        this.base = base;
        this.textures = fuckassTempNameTooTiredForThis(base, names);
        this.names = Arrays.asList(names);
    }

    public List<Identifier> getTextures() {
        return textures;
    }

    public List<String> getNames() {
        return names;
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
}
