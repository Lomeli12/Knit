package net.lomeli.knit.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public interface ICommand {
    /**
     * This is where you register the command and setup what arguments it takes,
     * as well as what suggestions appear for each argument.
     *
     * @param commandDispatcher
     */
    void setupCommand(CommandDispatcher<ServerCommandSource> commandDispatcher);

    String getName();
}
