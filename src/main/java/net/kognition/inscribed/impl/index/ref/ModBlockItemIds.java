package net.kognition.inscribed.impl.index.ref;

import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.references.BlockItemId;

/**
 * @author AcoYT
 */
public interface ModBlockItemIds {
    BlockItemId RUNE_WEAVER = create("rune_weaver");

    private static BlockItemId create(String id) {
        return BlockItemId.create(Inscribed.id(id), Inscribed.id(id));
    }
}
