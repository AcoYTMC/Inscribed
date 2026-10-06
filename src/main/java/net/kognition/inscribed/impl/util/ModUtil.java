package net.kognition.inscribed.impl.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.ModSounds;
import net.kognition.inscribed.impl.inventory.RuneWeaverMenu;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.SoundType;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

/**
 * @author AcoYT
 */
public class ModUtil {
    // Pearl Coordinates
    public static final int[] TOP = new int[]{119, 5};
    public static final int[] LEFT = new int[]{90, 34};
    public static final int[] BOTTOM = new int[]{119, 63};
    public static final int[] RIGHT = new int[]{148, 34};

    public static final int[] BIG = new int[]{111, 26};

    public static final SoundType SOUND_TYPE = new SoundType(
            SoundType.GILDED_BLACKSTONE.getVolume(),
            SoundType.GILDED_BLACKSTONE.getPitch(),
            ModSounds.RUNE_WEAVER_BREAK,
            SoundType.GILDED_BLACKSTONE.getStepSound(),
            ModSounds.RUNE_WEAVER_PLACE,
            SoundType.GILDED_BLACKSTONE.getHitSound(),
            SoundType.GILDED_BLACKSTONE.getFallSound()
    );

    public static <T> void scanDirectory(final ResourceManager manager, final FileToIdConverter lister, final DynamicOps<JsonElement> ops, final Codec<T> codec, final Map<Identifier, T> result) {
        for (Map.Entry<Identifier, Resource> entry : lister.listMatchingResources(manager).entrySet()) {
            Identifier location = entry.getKey();
            Identifier id = lister.fileToId(location);

            try {
                Reader reader = entry.getValue().openAsReader();

                try {
                    codec.parse(ops, StrictJsonParser.parse(reader)).ifSuccess(parsed -> {
                        if (result.putIfAbsent(id, parsed) != null) {
                            throw new IllegalStateException("Duplicate data file ignored with ID " + id);
                        }
                    }).ifError(error -> Inscribed.LOGGER.error("Couldn't parse data file '{}' from '{}': {}", id, location, error));
                } catch (Throwable var13) {
                    try {
                        reader.close();
                    } catch (Throwable var12) {
                        var13.addSuppressed(var12);
                    }

                    throw var13;
                }

                reader.close();
            } catch (JsonParseException | IllegalArgumentException | IOException e) {
                Inscribed.LOGGER.error("Couldn't parse data file '{}' from '{}'", id, location, e);
            }
        }
    }

    public static void insertLogic(Slot slot, RuneWeaverMenu menu) {
        if (!slot.getItem().isEmpty()) {
            menu.access.execute((level, pos) -> {
                if (level.isClientSide()) {
                    level.playSound(
                            null, pos,
                            ModSounds.RUNE_WEAVER_SELECT, SoundSource.BLOCKS,
                            1.0F, 1.0F
                    );
                }
            });
        }
    }
}
