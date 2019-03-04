package net.lomeli.knit.utils.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

public interface ISubCommand {
    void registerSubCommand(LiteralArgumentBuilder<ServerCommandSource> parentCommand);

    String getName();
}
