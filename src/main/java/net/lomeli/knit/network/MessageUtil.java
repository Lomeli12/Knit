package net.lomeli.knit.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.network.ClientSidePacketRegistryImpl;
import net.fabricmc.fabric.impl.network.ServerSidePacketRegistryImpl;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;

// Based off of TheOneProbe's packet handling by McJty
public class MessageUtil {
    public static void registerMessage(AbstractMessage message, EnvType side) {
        if (side == EnvType.CLIENT)
            ClientSidePacketRegistryImpl.INSTANCE.register(message.getMessageID(), message);
        else if (side == EnvType.SERVER)
            ServerSidePacketRegistryImpl.INSTANCE.register(message.getMessageID(), message);
    }

    @Environment(EnvType.CLIENT)
    public static void sendToServer(AbstractMessage message) {
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        message.toBytes(byteBuf);
        ClientSidePacketRegistryImpl.INSTANCE.sendToServer(message.getMessageID(), byteBuf);
    }

    public static void sendToClient(AbstractMessage message, ServerPlayerEntity player) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        message.toBytes(buf);
        ServerSidePacketRegistryImpl.INSTANCE.sendToPlayer(player, message.getMessageID(), buf);
    }
}
