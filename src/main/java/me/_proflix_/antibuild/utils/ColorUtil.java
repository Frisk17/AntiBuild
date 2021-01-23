package me._proflix_.antibuild.utils;

import org.bukkit.Bukkit;

import java.util.function.Function;

public class ColorUtil {

    private final static Function<String, String> FUNCTION;

    static {
        //If server version is 1.16 or higher, make the function translte from bungeecord-chat API
        if (Bukkit.getBukkitVersion().contains("1.16")) {
            FUNCTION = s -> net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', s);
            //Else make it compatible with the bukkit class
        } else {
            FUNCTION = s -> org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
        }
    }

    private ColorUtil() {
        throw new ExceptionInInitializerError("This class may not be initialized"); //Let no one invoke this class
    }

    //Method to invoke when we want to make a string colorized.
    public static String color(String line) {
        return FUNCTION.apply(line);
    }
}
