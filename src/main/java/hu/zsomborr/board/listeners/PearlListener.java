package hu.zsomborr.board.listeners;

import hu.zsomborr.board.utils.CC;
import hu.zsomborr.board.cooldowns.PearlCooldown;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import hu.zsomborr.board.zBoard;

public class PearlListener implements Listener {

    @EventHandler
    public void onPearl(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) return;
        if (item == null || item.getType() != Material.ENDER_PEARL) return;

        if (!zBoard.getInstance().getConfig().getBoolean("PEARL-IN-CREATIVE")) {
            if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SPECTATOR) {
                event.setCancelled(true);
                player.updateInventory();

                return;
            }
        }

        long now = System.currentTimeMillis();

        if (PearlCooldown.getPearlCooldown().containsKey(player.getUniqueId())) {
            long lastUse = PearlCooldown.getPearlCooldown().get(player.getUniqueId());
            long remaining = (lastUse + PearlCooldown.getCOOLDOWN_TIME()) - now;

            if (remaining > 0) {
                event.setCancelled(true);
                player.updateInventory();
                player.sendMessage(CC.translate(zBoard.getInstance().getConfig().getString("ON-COOLDOWN").replace("%cooldown%", PearlCooldown.formatRemaining(remaining))));

                return;
            }
        }

        PearlCooldown.getPearlCooldown().put(player.getUniqueId(), now);
    }
}
