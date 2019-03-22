package net.lomeli.knit.config.types;

import io.netty.buffer.ByteBuf;

public class NumberConfig extends ConfigField<Double> {
    public NumberConfig(String name, String comment, double value) {
        super(name, comment, value);
    }

    public NumberConfig(String name, double value) {
        super(name, value);
    }

    @Override
    public byte getTypeID() {
        return 1;
    }

    @Override
    public void toByte(ByteBuf byteBuf) {
        super.toByte(byteBuf);
        byteBuf.writeDouble(getConfigValue());
    }
}
