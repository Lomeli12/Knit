package net.lomeli.knit.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.networking.CustomPayloadPacketRegistry;
import net.minecraft.client.network.packet.CustomPayloadClientPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;

// Based off of TheOneProbe's packet handling by McJty
public class MessageUtil {
    public static void registerMessage(AbstractMessage message) {
        if (message.getSide() == EnvType.CLIENT)
            CustomPayloadPacketRegistry.CLIENT.register(message.getPacketID(), message);
        else if (message.getSide() == EnvType.SERVER)
            CustomPayloadPacketRegistry.SERVER.register(message.getPacketID(), message);
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(AbstractMessage message) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        message.toBytes(byteBuf);
    }

    public static void sendToClient(AbstractMessage message, ServerPlayerEntity player) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        message.toBytes(buf);
        player.networkHandler.sendPacket(new CustomPayloadClientPacket(message.getPacketID(), buf));
    }
}
