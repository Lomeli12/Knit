package net.lomeli.knit.config.types;

import io.netty.buffer.ByteBuf;

public class BooleanConfig extends ConfigField<Boolean> {
    public BooleanConfig(String name, String comment, boolean value) {
        super(name, comment, value);
    }

    public BooleanConfig(String name, boolean value) {
        super(name, value);
    }

    @Override
    public byte getTypeID() {
        return 2;
    }

    @Override
    public void toByte(ByteBuf byteBuf) {
        super.toByte(byteBuf);
        byteBuf.writeBoolean(getConfigValue());
    }
}
