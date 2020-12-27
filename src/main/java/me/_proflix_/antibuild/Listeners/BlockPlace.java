package me._proflix_.antibuild.Listeners;

import me._proflix_.antibuild.Main;
import me._proflix_.antibuild.Utils.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    private final Main instance;

    public BlockPlace(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (instance.isModuleEnabled("settings.block-place")
                && instance.isEnabledInList(event.getBlockPlaced().getWorld().getName(), "settings.block-place.worlds")) {
            event.setCancelled(true);
            if (player.hasPermission("antibuild.bypass.place")) {
                event.setCancelled(false);
            }
            if (event.isCancelled()) {
                player.sendMessage(ColorUtil.chat(instance.getConfig().getString("settings.block-place.no-permission")));
            }
        }
    }
}
