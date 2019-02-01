package net.lomeli.knit.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.networking.PacketContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.function.BiConsumer;

// Based off of TheOneProbe's packet handling by McJty
public abstract class AbstractMessage<T extends AbstractMessage> implements BiConsumer<PacketContext, PacketByteBuf> {

    public abstract T createMessage();

    public abstract void toBytes(PacketByteBuf byteBuf);

    public abstract void fromBytes(PacketByteBuf byteBuf);

    public abstract Identifier getPacketID();

    public abstract void handle(PacketContext context, T message);

    public abstract EnvType getSide();

    @Override
    public void accept(PacketContext context, PacketByteBuf byteBuf) {
        T message = createMessage();
        message.fromBytes(byteBuf);
        context.getTaskQueue().execute(() -> handle(context, message));
    }
}
