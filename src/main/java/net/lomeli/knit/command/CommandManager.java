package net.lomeli.knit.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class CommandManager implements Consumer<CommandDispatcher<ServerCommandSource>> {

    private List<ICommand> commandList = Lists.newArrayList();

    public void addCommand(ICommand command) {
        if (!commandList.contains(command))
            commandList.add(command);
    }

    public void registerCommands() {
        CommandRegistry.INSTANCE.register(false, this);
    }

    @Override
    public void accept(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        for (ICommand command : commandList) {
            command.setupCommand(commandDispatcher);
        }
    }

    public List<ICommand> getCommands() {
        return Collections.unmodifiableList(commandList);
    }
}
