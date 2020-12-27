package me._proflix_.antibuild;

import me._proflix_.antibuild.Listeners.BlockBreak;
import me._proflix_.antibuild.Listeners.BlockPlace;
import me._proflix_.antibuild.Utils.ColorUtil;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    // Plugin startup logic
    @Override
    public void onEnable() {
        saveDefaultConfig();

        ColorUtil.chat("Loading files...");
        loadFiles();
        ColorUtil.chat("Files loaded successfully.");

        ColorUtil.chat("Registering all listeners...");
        registerEvents();
        ColorUtil.chat("Listeners loaded successfully.");

        ColorUtil.chat("Plugin enabled successfully.");
    }

    // Plugin shutdown logic
    @Override
    public void onDisable() {
    }

    private void loadFiles() {
        saveIfNotExists();
    }

    private void saveIfNotExists() {
        if (!(new File(getDataFolder(), "config.yml").exists())) {
            ColorUtil.chat("File config.yml didn't exist, generating it...");
            saveResource("config.yml", false);
        }
    }

    private void registerEvents() {
        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new BlockBreak(this), this);
        pluginManager.registerEvents(new BlockPlace(this), this);
    }

    // Is the module enabled logic
    public boolean isModuleEnabled(String configPath) {
        configPath = configPath + ".enabled";

        if (getConfig().contains(configPath)) {
            return getConfig().getBoolean(configPath);
        } else {
            return false;
        }
    }

    // Per world stuff
    public boolean isEnabledInList(String item, String configPath) {
        if (getConfig().getBoolean(configPath + ".all")) {
            return true;
        } else {
            List<String> list = getConfig().getStringList(configPath + ".list");
            String mode = Objects.requireNonNull(getConfig().getString(configPath + ".mode")).toUpperCase();
            switch (mode) {
                case "WHITELIST":
                    return list.contains(item);
                case "BLACKLIST":
                    return !list.contains(item);
                default:
                    ColorUtil.chat("&7You must be using WHITELIST or BLACKLIST mode. Anything other is disabled.");
                    return false;
            }
        }
    }
}
