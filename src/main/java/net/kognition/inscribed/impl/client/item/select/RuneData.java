package net.kognition.inscribed.impl.client.item.select;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.component.RuneComponent;
import net.kognition.inscribed.impl.index.ModDataComponents;
import net.kognition.inscribed.impl.util.data.PearlCategory;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * @author AcoYT
 */
@Environment(EnvType.CLIENT)
public record RuneData() implements SelectItemModelProperty<RuneData.Data> {
    public static final Identifier ID = Inscribed.id("rune_data");
    public static final Type<RuneData, Data> TYPE = Type.create(MapCodec.unit(RuneData::new), Data.CODEC);

    @Nullable
    public Data get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner, int seed, ItemDisplayContext context) {
        RuneComponent component = stack.get(ModDataComponents.RUNE);
        if (component == null) return null;

        return new Data(component.category(), component.name());
    }

    public Codec<Data> valueCodec() {
        return Data.CODEC;
    }

    public Type<? extends SelectItemModelProperty<Data>, Data> type() {
        return TYPE;
    }

    public record Data(PearlCategory category, String name) {
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                PearlCategory.CODEC.fieldOf("category").forGetter(Data::category),
                Codec.STRING.fieldOf("name").forGetter(Data::name)
        ).apply(instance, Data::new));
    }
}
