package net.kognition.inscribed.impl.networking.serverbound;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kognition.inscribed.impl.Inscribed;
import net.kognition.inscribed.impl.menu.RuneWeaverMenu;
import net.kognition.inscribed.impl.util.data.PearlPlacement;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * @author AcoYT
 */
public record SetSelectedPayload(PearlPlacement selected) implements CustomPacketPayload {
    public static final Type<SetSelectedPayload> TYPE = new Type<>(Inscribed.id("set_selected"));

    public static final StreamCodec<ByteBuf, SetSelectedPayload> CODEC = StreamCodec.composite(
            PearlPlacement.STREAM_CODEC, SetSelectedPayload::selected,
            SetSelectedPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SetSelectedPayload> {
        public void receive(SetSelectedPayload payload, ServerPlayNetworking.Context context) {
            if (context.player().containerMenu instanceof RuneWeaverMenu weaverMenu) {
                weaverMenu.selected = payload.selected;
            }
        }
    }
}
