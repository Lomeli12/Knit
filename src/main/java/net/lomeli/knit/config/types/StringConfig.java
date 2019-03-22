package net.lomeli.knit.config.types;

import io.netty.buffer.ByteBuf;
import net.lomeli.knit.utils.network.ByteBuffUtil;

public class StringConfig extends ConfigField<String> {
    public StringConfig(String name, String comment, String value) {
        super(name, comment, value);
    }

    public StringConfig(String name, String value) {
        super(name, value);
    }

    @Override
    public byte getTypeID() {
        return 3;
    }

    @Override
    public void toByte(ByteBuf byteBuf) {
        super.toByte(byteBuf);
        ByteBuffUtil.writeUTF8String(byteBuf, getConfigValue());
    }
}
