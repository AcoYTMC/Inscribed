package net.kognition.inscribed.impl.networking;

import net.acoyt.acornlib.api.NetworkingInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kognition.inscribed.impl.networking.serverbound.ApplyRunePayload;
import net.kognition.inscribed.impl.networking.serverbound.SetSelectedPayload;

/**
 * @author AcoYT
 */
public interface ModNetworking {
    Impl IMPL = new Impl();

    static void registerCommon() {
        IMPL.registerTypes();
        IMPL.registerServerboundPackets();
    }

    @Environment(EnvType.CLIENT)
    static void registerClient() {
        IMPL.registerClientboundPackets();
    }

    class Impl implements NetworkingInitializer {
        public void registerTypes() {
            PayloadTypeRegistry.serverboundPlay().register(SetSelectedPayload.TYPE, SetSelectedPayload.CODEC);
            PayloadTypeRegistry.serverboundPlay().register(ApplyRunePayload.TYPE, ApplyRunePayload.CODEC);
        }

        public void registerServerboundPackets() {
            ServerPlayNetworking.registerGlobalReceiver(SetSelectedPayload.TYPE, new SetSelectedPayload.Receiver());
            ServerPlayNetworking.registerGlobalReceiver(ApplyRunePayload.TYPE, new ApplyRunePayload.Receiver());
        }

        @Environment(EnvType.CLIENT)
        public void registerClientboundPackets() {
            //
        }
    }
}
