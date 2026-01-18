package hu.zsomborr.board.listeners;

import fr.mrmicky.fastboard.FastBoard;
import hu.zsomborr.board.cooldowns.CombatCooldown;
import hu.zsomborr.board.cooldowns.PearlCooldown;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import hu.zsomborr.board.zBoard;

public class MainListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FastBoard board = new FastBoard(player);

        zBoard.getInstance().getProvider().getBoards().put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FastBoard board = zBoard.getInstance().getProvider().getBoards().remove(player.getUniqueId());

        PearlCooldown.remove(player);
        CombatCooldown.remove(player);

        if (board != null) board.delete();
        if (CombatCooldown.inCombat(player.getUniqueId())) player.setHealth(0);
    }
}
