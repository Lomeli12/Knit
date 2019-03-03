package net.lomeli.knit.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;

public class MasterCommand implements ICommand {
    private final String name;
    private List<ISubCommand> subCommands = Lists.newArrayList();

    public MasterCommand(String name) {
        this.name = name;
    }

    public void registerCommand(ISubCommand subCommand) {
        if (!subCommands.contains(subCommand))
            subCommands.add(subCommand);
    }

    @Override
    public void setupCommand(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> parentCommand = ServerCommandManager.literal(getName());

        for (ISubCommand subCommand : subCommands) {
            subCommand.registerSubCommand(parentCommand);
        }

        commandDispatcher.register(parentCommand);
    }

    @Override
    public String getName() {
        return name;
    }
}
