package net.lomeli.knit.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

// Based off of TheOneProbe's packet handling by McJty
public class ByteBuffUtil {

    public static String readUTF8String(ByteBuf byteBuf) {
        // Number of bytes the string takes up.
        int byteSize = byteBuf.readInt();
        if (byteSize == -1)
            return null;
        if (byteSize == 0) // Zero length string = empty string.
            return "";
        // Get string bytes as array, then create string with UTF-8 encoding.
        byte[] stringBytes = new byte[byteSize];
        byteBuf.readBytes(stringBytes);
        return new String(stringBytes, StandardCharsets.UTF_8);
    }

    public static void writeUTF8String(ByteBuf byteBuf, String str) {
        // If null, use -1 to represent null string
        if (str == null) {
            byteBuf.writeInt(-1);
            return;
        }
        // Convert string into UTF-8 bytes
        byte[] stringBytes = str.getBytes(StandardCharsets.UTF_8);
        // Write byte array length as the "header" of the packet.
        byteBuf.writeInt(stringBytes.length);
        // If there are actual bytes to write, write them.
        if (stringBytes.length > 0)
            byteBuf.writeBytes(stringBytes);
    }

    public static Identifier readIdentifier(ByteBuf byteBuf) {
        String namespace = readUTF8String(byteBuf);
        String path = readUTF8String(byteBuf);
        return new Identifier(namespace, path);
    }

    public static void writeIdentifier(ByteBuf byteBuf, Identifier identifier) {
        writeUTF8String(byteBuf, identifier.getNamespace());
        writeUTF8String(byteBuf, identifier.getPath());
    }
}
