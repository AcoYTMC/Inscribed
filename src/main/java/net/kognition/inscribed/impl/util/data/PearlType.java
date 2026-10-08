package net.kognition.inscribed.impl.util.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * @author AcoYT
 */
public record PearlType(TagKey<Item> targetTag, PearlCategory category) {
    public static final Codec<PearlType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TagKey.codec(Registries.ITEM).fieldOf("targetTag").forGetter(PearlType::targetTag),
            PearlCategory.CODEC.fieldOf("category").forGetter(PearlType::category)
    ).apply(instance, PearlType::new));

    public static final Codec<Holder<PearlType>> CODEC = RegistryCodecs.holder(Inscribed.PEARL_DATA_KEY, DIRECT_CODEC);
    public static final Codec<HolderSet<PearlType>> LIST_CODEC = RegistryCodecs.holderSet(Inscribed.PEARL_DATA_KEY);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<PearlType>> STREAM_CODEC = ByteBufCodecs.holderRegistry(Inscribed.PEARL_DATA_KEY);
    public static final StreamCodec<RegistryFriendlyByteBuf, HolderSet<PearlType>> LIST_STREAM_CODEC = ByteBufCodecs.holderSet(Inscribed.PEARL_DATA_KEY);
}
