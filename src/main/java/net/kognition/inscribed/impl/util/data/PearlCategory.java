package net.kognition.inscribed.impl.util.data;

import com.mojang.serialization.Codec;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AcoYT
 */
public enum PearlCategory implements StringRepresentable {
    DEXTERITY("dexterity", "gold", "amber", "ochre", "saffron"),
    PRESERVATION("preservation", "cerulean", "azure", "navy", "turquoise"),
    RESTORATION("restoration", "pear", "jade", "moss", "olive"),
    RIGOROUS("rigorous", "ruby", "brick", "garnet", "merlot");

    public static final Codec<PearlCategory> CODEC = StringRepresentable.fromEnum(PearlCategory::values);

    private final String base;
    private final List<Identifier> textures;

    PearlCategory(String base, String... names) {
        this.base = base;
        this.textures = fuckassTempNameTooTiredForThis(base, names);
    }

    public List<Identifier> getTextures() {
        return textures;
    }

    /**
     * Gets the texture based on the selected pearl
     */
    public Identifier getTexture(PearlPlacement selected) {
        if (selected == PearlPlacement.TOP && hasPlacement(selected)) return textures.getFirst();
        if (selected == PearlPlacement.RIGHT && hasPlacement(selected)) return textures.get(1);
        if (selected == PearlPlacement.LEFT && hasPlacement(selected)) return textures.get(2);
        if (selected == PearlPlacement.BOTTOM && hasPlacement(selected)) return textures.get(3);
        return null;
    }

    public boolean hasPlacement(PearlPlacement placement) {
        return switch (placement) {
            case TOP -> !textures.isEmpty();
            case RIGHT -> textures.size() > 1;
            case LEFT -> textures.size() > 2;
            case BOTTOM -> textures.size() > 3;
            case BIG -> true;
            case null -> false;
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
