package net.kognition.inscribed.impl.util.data;

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

    private final String base;
    private final List<Identifier> textures;

    PearlCategory(String base, String... names) {
        this.base = base;
        this.textures = fuckassTempNameTooTiredForThis(base, names);
    }

    public List<Identifier> getTextures() {
        return textures;
    }

    public Identifier get(PearlPlacement placement) {
        if (placement == PearlPlacement.TOP) return textures.getFirst();
        if (placement == PearlPlacement.RIGHT) return textures.get(1);
        if (placement == PearlPlacement.BOTTOM) return textures.get(2);
        if (placement == PearlPlacement.LEFT) return textures.get(3);
        return null;
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
