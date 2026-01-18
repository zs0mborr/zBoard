package hu.zsomborr.board.listeners;

import hu.zsomborr.board.cooldowns.CombatCooldown;
import hu.zsomborr.board.cooldowns.PearlCooldown;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import hu.zsomborr.board.zBoard;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damagerEntity = event.getDamager();

        if (!(entity instanceof Player)) return;
        if (!(damagerEntity instanceof Player)) return;
        if (event.isCancelled()) return;

        Player victim = (Player) entity;
        Player damager = (Player) damagerEntity;

        if (victim.getGameMode() == GameMode.CREATIVE) return;

        long now = System.currentTimeMillis();

        CombatCooldown.getCombatCooldown().put(victim.getUniqueId(), now);
        if (damager.getGameMode() != GameMode.CREATIVE) CombatCooldown.getCombatCooldown().put(damager.getUniqueId(), now);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (!zBoard.getInstance().getConfig().getBoolean("RESET-COOLDOWNS-ON-DEATH")) return;

        CombatCooldown.remove(player);
        PearlCooldown.remove(player);
    }
}
