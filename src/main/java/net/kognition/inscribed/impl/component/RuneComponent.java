package net.kognition.inscribed.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.kognition.inscribed.impl.util.data.PearlCategory;
import net.kognition.inscribed.impl.util.data.PearlPlacement;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

/**
 * @author AcoYT
 * Completely temp for now until functionality is specified
 */
public record RuneComponent(PearlCategory category, PearlCategory.Type type) implements TooltipProvider {
    public static final RuneComponent DEFAULT = new RuneComponent(PearlCategory.PRESERVATION, PearlCategory.PRESERVATION.getPairs().getFirst());

    public static final Codec<RuneComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PearlCategory.CODEC.fieldOf("category").forGetter(RuneComponent::category),
            PearlCategory.Type.CODEC.fieldOf("type").forGetter(RuneComponent::type)
    ).apply(instance, RuneComponent::new));

    public static final StreamCodec<ByteBuf, RuneComponent> STREAM_CODEC = StreamCodec.composite(
            PearlCategory.STREAM_CODEC, RuneComponent::category,
            PearlCategory.Type.STREAM_CODEC, RuneComponent::type,
            RuneComponent::new
    );

    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        Component categoryName = Component.translatable("inscribed.category." + category.getSerializedName());
        Component typeName = Component.translatable("inscribed.category." + category.getSerializedName() + "." + type.name());

        consumer.accept(Component.translatable("tooltip.inscribed.pearl_category", categoryName, typeName).withColor(type.color()));
    }

    public static class Builder {
        public static RuneComponent create(RuneWeaverMenu menu) {
            if (menu.type == null || menu.selected == PearlPlacement.NONE) return null;
            return new RuneComponent(menu.type.category(), menu.type.category().getPair(menu.selected));
        }
    }
}
