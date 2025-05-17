package io.github.mtn16.wolfCore.Commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WolfCommand {
    private Plugin plugin;

    private CommandExecutor executor;
    private TabCompleter tabCompleter;

    private String name;
    private String description;
    private String usage;
    private String permission;

    private boolean playerOnly;
    private boolean consoleOnly;

    public Plugin getPlugin() {
        return plugin;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public TabCompleter getTabCompleter() {
        return tabCompleter;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(getPermission());
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public boolean isConsoleOnly() {
        return consoleOnly;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public void setTabCompleter(TabCompleter completer) {
        this.tabCompleter = completer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setPlayerOnly() {
        this.playerOnly = true;
    }

    public void setConsoleOnly() {
        this.consoleOnly = true;
    }

    public void setPlayerOnly(boolean playerOnly) {
        this.playerOnly = playerOnly;
    }

    public void setConsoleOnly(boolean consoleOnly) {
        this.consoleOnly = consoleOnly;
    }
}
