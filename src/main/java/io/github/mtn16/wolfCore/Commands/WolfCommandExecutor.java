package io.github.mtn16.wolfCore.Commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WolfCommandExecutor implements CommandExecutor {

    private final WolfCommand wolfCommand;


    public WolfCommandExecutor(WolfCommand wolfCommand) {
        this.wolfCommand = wolfCommand;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(wolfCommand.isConsoleOnly() && (sender instanceof Player))
        {
            sender.sendMessage(Component.text("Only console can execute this command!"));
            return true;
        }

        if(wolfCommand.isPlayerOnly() && (sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(Component.text("Only players can execute this command!"));
            return true;
        }

        return wolfCommand.getExecutor().onCommand(sender, command, label, args);
    }
}
