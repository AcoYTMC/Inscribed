package net.kognition.inscribed.impl.util.data;

import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import net.kognition.inscribed.impl.util.ModUtil;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AcoYT
 */
public class PearlTypeReloadListener extends SimpleReloadListener<Map<Identifier, PearlType>> {
    public static final String PATH = "pearl_types";
    public static final Map<Identifier, PearlType> TYPES = new HashMap<>();
    public static final Map<PearlType, Identifier> BACKWARDS = new HashMap<>();

    public Map<Identifier, PearlType> prepare(SharedState state) {
        Map<Identifier, PearlType> raw = new HashMap<>();
        ModUtil.scanDirectory(state.resourceManager(), FileToIdConverter.json(PATH),
                state.get(ResourceLoader.REGISTRY_LOOKUP_KEY).createSerializationContext(JsonOps.INSTANCE),
                PearlType.DIRECT_CODEC, raw
        );

        return raw;
    }

    public void apply(Map<Identifier, PearlType> preparations, SharedState state) {
        TYPES.clear();
        TYPES.putAll(preparations);

        BACKWARDS.clear();
        preparations.forEach((id, data) -> BACKWARDS.put(data, id));
    }

    public static boolean isInAny(ItemStack stack) {
        List<TagKey<Item>> itemTags = new ArrayList<>();

        for (PearlType type : TYPES.values()) {
            itemTags.add(type.targetTag());
        }

        for (TagKey<Item> tagKey : itemTags) {
            if (stack.is(tagKey)) return true;
        }

        return false;
    }

    public static PearlType getFromVariables(ItemStack stack) {
        for (PearlType type : TYPES.values()) {
            if (stack.is(type.targetTag())) {
                return type;
            }
        }

        return null;
    }
}
