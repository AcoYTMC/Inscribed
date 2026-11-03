package net.kognition.inscribed.impl.client.item.select;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.client.math_type.MathDefinition;
import net.kognition.inscribed.impl.client.math_type.MathInit;
import net.kognition.inscribed.impl.component.ClusteredComponent;
import net.kognition.inscribed.impl.index.ModDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * @author AcoYT
 */
public record ClusteredProperty(List<MathDefinition> stages) implements SelectItemModelProperty<Integer> {
    public static final Identifier ID = Inscribed.id("clustered");

    public static final MapCodec<ClusteredProperty> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            MathInit.CODEC.listOf().fieldOf("stages").forGetter(ClusteredProperty::stages)
    ).apply(instance, ClusteredProperty::new));

    public static final Type<ClusteredProperty, Integer> TYPE = Type.create(CODEC, Codec.INT);

    public Type<ClusteredProperty, Integer> type() {
        return TYPE;
    }

    public Codec<Integer> valueCodec() {
        return Codec.INT;
    }

    public Integer get(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext displayContext) {
        if (!itemStack.has(ModDataComponents.CLUSTERED)) return 0;
        for (int i = 0; i < stages.size(); i++) {
            MathDefinition definition = stages.get(i);
            if (definition.test(ClusteredComponent.get(itemStack).charges())) {
                return i;
            }
        }

        return 0;
    }
}
