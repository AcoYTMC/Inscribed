package net.kognition.inscribed.impl.networking.serverbound;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.component.RuneComponent;
import net.kognition.inscribed.impl.index.ModDataComponents;
import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * @author AcoYT
 */
public record ApplyRunePayload() implements CustomPacketPayload {
    public static final Type<ApplyRunePayload> TYPE = new Type<>(Inscribed.id("apply_config"));

    public static final StreamCodec<ByteBuf, ApplyRunePayload> CODEC = StreamCodec.unit(new ApplyRunePayload());

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<ApplyRunePayload> {
        public void receive(ApplyRunePayload payload, ServerPlayNetworking.Context context) {
            if (context.player().containerMenu instanceof RuneWeaverMenu weaverMenu) {
                weaverMenu.pearlSlot.getItem().shrink(1);
                weaverMenu.runeSlot.getItem().set(ModDataComponents.RUNE, RuneComponent.Builder.create(weaverMenu));
            }
        }
    }
}
