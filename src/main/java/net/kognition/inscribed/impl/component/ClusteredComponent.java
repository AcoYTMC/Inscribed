package net.kognition.inscribed.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kognition.inscribed.impl.index.ModDataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

/**
 * @author AcoYT
 */
public record ClusteredComponent(int charges, int maxCharges) {
    public static final ClusteredComponent DEFAULT = new ClusteredComponent(0, 5);

    public static final Codec<ClusteredComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("charges", DEFAULT.charges).forGetter(ClusteredComponent::charges),
            Codec.INT.optionalFieldOf("maxCharges", DEFAULT.maxCharges).forGetter(ClusteredComponent::maxCharges)
    ).apply(instance, ClusteredComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClusteredComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClusteredComponent::charges,
            ByteBufCodecs.INT, ClusteredComponent::maxCharges,
            ClusteredComponent::new
    );

    public static ClusteredComponent get(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.CLUSTERED, DEFAULT);
    }

    public static ClusteredComponent.Builder getModifiable(ItemStack stack, boolean basedOff) {
        return new Builder(stack, basedOff);
    }

    public ClusteredComponent withCharges(int charges) {
        return new ClusteredComponent(charges, maxCharges);
    }

    public boolean isFullyCharged() {
        return charges >= maxCharges;
    }

    public static class Builder {
        private int charges = DEFAULT.charges;
        private int maxCharges = DEFAULT.maxCharges;
        private final ItemStack stack;

        private Builder(ItemStack stack, boolean basedOff) {
            this.stack = stack;
            if (basedOff && stack.has(ModDataComponents.CLUSTERED)) {
                ClusteredComponent base = get(stack);
                charges = base.charges;
                maxCharges = base.maxCharges;
            }
        }

        public Builder charges(int amount) {
            charges = amount;
            return this;
        }

        public Builder increment(int amount) {
            charges = Mth.clamp(charges + amount, 0, maxCharges);
            return this;
        }

        public void apply() {
            stack.set(ModDataComponents.CLUSTERED, new ClusteredComponent(charges, maxCharges));
        }
    }
}
