package net.kognition.inscribed.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author AcoYT
 * Completely temp for now until functionality is specified
 */
public record RuneComponent() {
    public static final Codec<RuneComponent> CODEC = RecordCodecBuilder.create(instance -> instance.point(new RuneComponent()));
    public static final StreamCodec<ByteBuf, RuneComponent> STREAM_CODEC = StreamCodec.unit(new RuneComponent());

    public static class Builder {
        public static RuneComponent create(RuneWeaverMenu menu) {
            return new RuneComponent();
        }
    }
}
