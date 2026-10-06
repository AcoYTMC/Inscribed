package net.kognition.inscribed.impl.index;

import net.acoyt.acornlib.api.registrants.SoundEventRegistrant;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.sounds.SoundEvent;

/**
 * @author AcoYT
 */
public interface ModSounds {
    SoundEventRegistrant SOUNDS = new SoundEventRegistrant(Inscribed.MOD_ID);

    SoundEvent RUNE_WEAVER_OPEN = SOUNDS.register("block.rune_weaver.open");
    SoundEvent RUNE_WEAVER_BREAK = SOUNDS.register("block.rune_weaver.break");
    SoundEvent RUNE_WEAVER_PLACE = SOUNDS.register("block.rune_weaver.place");

    SoundEvent RUNE_WEAVER_SELECT = SOUNDS.register("block.rune_weaver.select");
    SoundEvent RUNE_WEAVER_DESELECT = SOUNDS.register("block.rune_weaver.deselect");

    SoundEvent RUNE_WEAVER_CONFIRM = SOUNDS.register("block.rune_weaver.confirm");
    SoundEvent RUNE_WEAVER_UNAVAILABLE = SOUNDS.register("block.rune_weaver.unavailable");

    static void init() {}
}
