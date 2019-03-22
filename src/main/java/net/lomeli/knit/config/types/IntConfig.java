package net.lomeli.knit.config.types;

import io.netty.buffer.ByteBuf;

public class IntConfig extends ConfigField<Integer> {
    public IntConfig(String name, String comment, int value) {
        super(name, comment, value);
    }

    public IntConfig(String name, int value) {
        super(name, value);
    }

    @Override
    public byte getTypeID() {
        return 0;
    }

    @Override
    public void toByte(ByteBuf byteBuf) {
        super.toByte(byteBuf);
        byteBuf.writeInt(getConfigValue());
    }
}
