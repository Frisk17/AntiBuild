package me._proflix_.antibuild.listeners;

import me._proflix_.antibuild.Main;
import me._proflix_.antibuild.utils.ColorUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Objects;

public class BlockPlace implements Listener {

    private final Main instance;

    public BlockPlace(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
        Block material = event.getBlock();
        String block = event.getBlock().getType().toString();
        Player player = event.getPlayer();

        if (instance.isModuleEnabled("settings.block-place")) {

            if (instance.isEnabledInList(event.getBlock().getWorld().getName(), "settings.block-place.worlds")) {
                event.setCancelled(true);

                if (player.hasPermission(Objects.requireNonNull(instance.getConfig().getString("settings.block-place.permission")))) {
                    event.setCancelled(false);
                } else if (event.isCancelled()) {
                    player.sendMessage(ColorUtil.color(instance.getConfig().getString("settings.block-place.no-permission")));
                }

                if (instance.isModuleEnabled("settings.block-place.per-blocks") && instance.isEnabledInList(material.getType().toString().toUpperCase(), "settings.block-place.per-blocks.blocks")) {
                    event.setCancelled(!player.hasPermission(Objects.requireNonNull(instance.getConfig().getString("settings.block-place.per-blocks.permission")) + block));
                    if (event.isCancelled()) {
                        player.sendMessage(ColorUtil.color(Objects.requireNonNull(instance.getConfig().getString("settings.block-place.per-blocks.no-permission")).replace("<block>", material.getType().name())));
                    }
                }
            }
        }
    }
}