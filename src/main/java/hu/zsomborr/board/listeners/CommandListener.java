package hu.zsomborr.board.listeners;

import hu.zsomborr.board.cooldowns.CombatCooldown;
import hu.zsomborr.board.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import hu.zsomborr.board.zBoard;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    private final List<String> blockedCommands = zBoard.getInstance().getConfig().getStringList("BLOCKED-COMBAT-COMMANDS");

    @EventHandler
    public void onExecute(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (zBoard.getInstance().getConfig().getBoolean("CMD-OP-BYPASS")) {
            if (player.isOp()) return;
        }

        if (!CombatCooldown.inCombat(player.getUniqueId())) return;

        String message = event.getMessage().toLowerCase();
        String command = message.substring(1).split(" ")[0];

        if (blockedCommands.contains("*")) {
            event.setCancelled(true);
            player.sendMessage(CC.translate(zBoard.getInstance().getConfig().getString("COMMAND-BLOCKED")));
            return;
        }

        if (blockedCommands.contains(command)) {
            event.setCancelled(true);
            player.sendMessage(CC.translate(zBoard.getInstance().getConfig().getString("COMMAND-BLOCKED")));
        }
    }
}
