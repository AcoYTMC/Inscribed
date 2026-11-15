package net.kognition.inscribed.impl.util;

import net.kognition.inscribed.impl.index.ModSounds;
import net.minecraft.world.level.block.SoundType;

/**
 * @author AcoYT
 */
public class ModUtil {
    public static final SoundType SOUND_TYPE = new SoundType(
            SoundType.GILDED_BLACKSTONE.getVolume(),
            SoundType.GILDED_BLACKSTONE.getPitch(),
            ModSounds.RUNE_WEAVER_BREAK,
            SoundType.GILDED_BLACKSTONE.getStepSound(),
            ModSounds.RUNE_WEAVER_PLACE,
            SoundType.GILDED_BLACKSTONE.getHitSound(),
            SoundType.GILDED_BLACKSTONE.getFallSound()
    );
}
