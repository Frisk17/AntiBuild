package me._proflix_.antibuild.Listeners;

import me._proflix_.antibuild.Main;
import me._proflix_.antibuild.Utils.ColorUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;

public class BlockBreak implements Listener {

    private final Main instance;

    public BlockBreak(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
        Block material = event.getBlock();
        Player player = event.getPlayer();

        if (instance.isModuleEnabled("settings.block-break")) {

            if (instance.isEnabledInList(event.getBlock().getWorld().getName(), "settings.block-break.worlds")) {
                event.setCancelled(true);

                if (instance.isModuleEnabled("settings.block-break.per-blocks") && instance.isEnabledInList(material.getType().toString().toUpperCase(), "settings.block-break.per-blocks.blocks")) {
                    event.setCancelled(true);

                    player.sendMessage(ColorUtil.chat(Objects.requireNonNull(instance.getConfig().getString("settings.block-break.per-blocks.no-permission")).replace("<block>", material.getType().name())));

                } else if (event.isCancelled()) {
                        player.sendMessage(ColorUtil.chat(instance.getConfig().getString("settings.block-break.no-permission")));
                    }
                }

                if (player.hasPermission(Objects.requireNonNull(instance.getConfig().getString("settings.block-break.permission")))) {
                    event.setCancelled(false);
                }
        }
    }
}
