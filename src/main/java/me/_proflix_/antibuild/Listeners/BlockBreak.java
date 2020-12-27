package me._proflix_.antibuild.Listeners;

import me._proflix_.antibuild.Main;
import me._proflix_.antibuild.Utils.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private final Main instance;

    public BlockBreak(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (instance.isModuleEnabled("settings.block-break")
                && instance.isEnabledInList(event.getBlock().getWorld().getName(), "settings.block-break.worlds")) {
            event.setCancelled(true);
            if (player.hasPermission("antibuild.bypass.break")) {
                event.setCancelled(false);
            }
            if (event.isCancelled()) {
                player.sendMessage(ColorUtil.chat(instance.getConfig().getString("settings.block-break.no-permission")));
            }
        }
    }
}
