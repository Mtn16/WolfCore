package io.github.mtn16.wolfCore;

import io.github.mtn16.wolfCore.Commands.WolfCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class WolfCore extends JavaPlugin {

    private static WolfCore instance;
    @Override
    public void onEnable() {
        instance = this;
        WolfCommandManager.init(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static WolfCore getInstance() {
        return instance;
    }
}
