package net.kognition.inscribed.data.provider.client;

import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.RegistrationBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.RegistrationType;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.index.ModSounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

import static net.kognition.inscribed.impl.Inscribed.id;

/**
 * @author AcoYT
 */
public class ModSoundsProvider extends FabricSoundsProvider {
    public ModSoundsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public void configure(HolderLookup.Provider provider, SoundExporter exporter) {
        exporter.add(ModSounds.RUNE_WEAVER_OPEN, builder(id("block/rune_weaver/open"))
                .subtitle("subtitles.inscribed.block.rune_weaver.open"));
        exporter.add(ModSounds.RUNE_WEAVER_BREAK, builder(id("block/rune_weaver/break"), 3)
                .subtitle("subtitles.inscribed.block.rune_weaver.break"));
        exporter.add(ModSounds.RUNE_WEAVER_PLACE, builder(id("block/rune_weaver/place"), 3)
                .subtitle("subtitles.inscribed.block.rune_weaver.place"));

        exporter.add(ModSounds.RUNE_WEAVER_SELECT, builder(id("block/rune_weaver/select"), 3)
                .subtitle("subtitles.inscribed.block.rune_weaver.select"));
        exporter.add(ModSounds.RUNE_WEAVER_DESELECT, builder(id("block/rune_weaver/deselect"))
                .subtitle("subtitles.inscribed.block.rune_weaver.deselect"));

        exporter.add(ModSounds.RUNE_WEAVER_CONFIRM, builder(id("block/rune_weaver/confirm"))
                .subtitle("subtitles.inscribed.block.rune_weaver.confirm"));
        exporter.add(ModSounds.RUNE_WEAVER_UNAVAILABLE, builder(id("block/rune_weaver/unavailable"))
                .subtitle("subtitles.inscribed.block.rune_weaver.unavailable"));
    }

    private static SoundTypeBuilder builder(Identifier id) {
        return SoundTypeBuilder.of().sound(RegistrationBuilder.create(RegistrationType.FILE, id));
    }

    private static SoundTypeBuilder builder(Identifier id, int count) {
        return SoundTypeBuilder.of().sound(RegistrationBuilder.create(RegistrationType.FILE, id), count);
    }

    public String getName() {
        return Inscribed.MOD_ID + "_sounds";
    }
}
