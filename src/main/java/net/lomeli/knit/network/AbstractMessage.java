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

    public abstract Identifier getMessageID();

    public abstract void handle(PacketContext context, T message);

    /**
     * Side this packet is being sent to.
     * <br /><br />
     * Example:
     * <br /> Server -> Client = EnvType.CLIENT
     * <br /> Client -> Server = EnvType.SERVER
     * <br />
     * @return The side this packet is being sent to.
     */
    public abstract EnvType getTargetSide();

    @Override
    public void accept(PacketContext context, PacketByteBuf byteBuf) {
        T message = createMessage();
        message.fromBytes(byteBuf);
        context.getTaskQueue().execute(() -> handle(context, message));
    }
}
