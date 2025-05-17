package io.github.mtn16.wolfCore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@SuppressWarnings("UnstableApiUsage")
public class WolfCommandManager {
    private static CommandMap commandMap;

    public static void init(Plugin plugin) {
        if (commandMap == null) {
            try {
                Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getServer());
                plugin.getLogger().info("WolfCore command API has been loaded successfully.");
            } catch (Exception e) {
                plugin.getLogger().severe("An error occurred while attempting to load WolfCore command API.");
                e.printStackTrace();
            }
        }
    }

    public static boolean registerCommand(WolfCommand wolfCommand) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            PluginCommand command = constructor.newInstance(wolfCommand.getName(), wolfCommand.getPlugin());

            if(wolfCommand.getExecutor() == null || wolfCommand.getName() == null || wolfCommand.getUsage() == null || wolfCommand.getDescription() == null) {
                return false;
            }

            command.setExecutor(new WolfCommandExecutor(wolfCommand));
            command.setDescription(wolfCommand.getDescription());
            command.setUsage(wolfCommand.getUsage());
            command.setPermission(wolfCommand.getPermission());

            if(wolfCommand.getTabCompleter() != null) {
                command.setTabCompleter(wolfCommand.getTabCompleter());
            }

            commandMap.register(wolfCommand.getPlugin().getDescription().getName(), command);
            return true;
        } catch (Exception e) {
            Bukkit.getLogger().severe("An error occurred while attempting to register command" + wolfCommand.getName());
            e.printStackTrace();
            return false;
        }
    }
}
