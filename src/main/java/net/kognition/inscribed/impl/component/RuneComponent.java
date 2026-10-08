package net.kognition.inscribed.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.kognition.inscribed.impl.util.data.PearlCategory;
import net.kognition.inscribed.impl.util.data.PearlPlacement;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/**
 * @author AcoYT
 * Completely temp for now until functionality is specified
 */
public record RuneComponent(PearlCategory category, String name) {
    public static final RuneComponent DEFAULT = new RuneComponent(PearlCategory.PRESERVATION, PearlCategory.PRESERVATION.getNames().getFirst());

    public static final Codec<RuneComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PearlCategory.CODEC.fieldOf("category").forGetter(RuneComponent::category),
            Codec.STRING.fieldOf("name").forGetter(RuneComponent::name)
    ).apply(instance, RuneComponent::new));

    public static final StreamCodec<ByteBuf, RuneComponent> STREAM_CODEC = StreamCodec.composite(
            PearlCategory.STREAM_CODEC, RuneComponent::category,
            ByteBufCodecs.STRING_UTF8, RuneComponent::name,
            RuneComponent::new
    );

    public static class Builder {
        public static RuneComponent create(RuneWeaverMenu menu) {
            if (menu.type == null || menu.selected == PearlPlacement.NONE) return null;
            return new RuneComponent(menu.type.category(), menu.type.category().getName(menu.selected));
        }
    }
}
