package net.lomeli.knit.config.types;

import io.netty.buffer.ByteBuf;
import net.lomeli.knit.utils.network.ByteBuffUtil;

import javax.annotation.Nonnull;

public abstract class ConfigField<T> {
    private String configName;
    private String comment;
    private T configValue;

    public ConfigField(@Nonnull String configName, @Nonnull String comment, @Nonnull T configValue) {
        this.configName = configName;
        this.comment = comment;
        this.configValue = configValue;
    }

    public ConfigField(String configName, T configValue) {
        this(configName, "", configValue);
    }

    public String getConfigName() {
        return configName;
    }

    public String getComment() {
        return comment;
    }

    public T getConfigValue() {
        return configValue;
    }

    public void setConfigValue(T configValue) {
        this.configValue = configValue;
    }

    public abstract byte getTypeID();

    // Override this!
    public void toByte(ByteBuf byteBuf) {
        byteBuf.writeByte(getTypeID());
        ByteBuffUtil.writeUTF8String(byteBuf, getConfigName());
    }

    public static ConfigField fromByte(ByteBuf byteBuf) {
        byte id = byteBuf.readByte();
        String name = ByteBuffUtil.readUTF8String(byteBuf);
        switch (id) {
            case 0:
                return new IntConfig(name, byteBuf.readInt());
            case 1:
                return new NumberConfig(name, byteBuf.readDouble());
            case 2:
                return new BooleanConfig(name, byteBuf.readBoolean());
            case 3:
                return new StringConfig(name, ByteBuffUtil.readUTF8String(byteBuf));
            default:
                return null;
        }
    }
}
