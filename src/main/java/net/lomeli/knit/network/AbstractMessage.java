package net.lomeli.knit.network;

import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

// Based off of TheOneProbe's packet handling by McJty
public abstract class AbstractMessage<T extends AbstractMessage> implements PacketConsumer {

    public abstract T createMessage();

    public abstract void toBytes(PacketByteBuf byteBuf);

    public abstract void fromBytes(PacketByteBuf byteBuf);

    public abstract Identifier getMessageID();

    public abstract void handle(PacketContext context, T message);

    @Override
    public void accept(PacketContext context, PacketByteBuf byteBuf) {
        T message = createMessage();
        message.fromBytes(byteBuf);
        context.getTaskQueue().execute(() -> handle(context, message));
    }
}
