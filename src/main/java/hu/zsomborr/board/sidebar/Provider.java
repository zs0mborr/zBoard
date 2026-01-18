package hu.zsomborr.board.sidebar;

import java.util.*;

import fr.mrmicky.fastboard.FastBoard;
import hu.zsomborr.board.cooldowns.CombatCooldown;
import hu.zsomborr.board.utils.CC;
import hu.zsomborr.board.cooldowns.PearlCooldown;
import hu.zsomborr.board.utils.Utils;
import hu.zsomborr.board.zBoard;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class Provider {

    private final zBoard plugin = zBoard.getInstance();
    @Getter
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private int titleIndex = 0;
    private int footerIndex = 0;
    private String cachedTitle = "";
    private String cachedFooter = "";

    public void updateTask() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (FastBoard board : boards.values()) {
                updateBoard(board);
            }
        }, 0, 2);

        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            List<String> titles = plugin.getConfig().getStringList("TITLE");
            if (titles.isEmpty()) return;

            if (titleIndex >= titles.size()) titleIndex = 0;

            cachedTitle = CC.translate(titles.get(titleIndex));

            titleIndex++;

        }, 0L, 20);

        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            List<String> footers = plugin.getConfig().getStringList("FOOTER");
            if (footers.isEmpty()) return;

            if (footerIndex >= footers.size()) footerIndex = 0;

            cachedFooter = footers.get(footerIndex);
            footerIndex++;

        }, 0L, 20);
    }

    public void updateBoard(FastBoard board) {
        List<String> lines = new ArrayList<>();
        Player player = board.getPlayer();

        if (zBoard.getInstance().getConfig().getStringList("BLOCKED-WORLDS").contains(player.getWorld().getName())) return;

        for (String line : plugin.getConfig().getStringList("GLOBAL-LINES")) {

            if (line.equalsIgnoreCase("%pearl%")) {
                if (PearlCooldown.hasPearlCooldown(player.getUniqueId())) {
                    lines.add(CC.translate(zBoard.getInstance().getConfig().getString("ADDITIONS.PEARL").replace("%cooldown%", PearlCooldown.formatRemaining(PearlCooldown.getPearlCooldown(player)))));
                }
                continue;
            }

            if (line.equalsIgnoreCase("%combat%")) {
                if (CombatCooldown.inCombat(player.getUniqueId())) {
                    lines.add(CC.translate(zBoard.getInstance().getConfig().getString("ADDITIONS.COMBAT").replace("%cooldown%", CombatCooldown.formatRemaining(CombatCooldown.getCombatCooldown(player)))));
                }
                continue;
            }

            int kills = 0;
            try {
                kills = player.getStatistic(Statistic.PLAYER_KILLS);
            } catch (Exception ignored) {
            }

            line = line
                    .replace("%kills%", String.valueOf(kills))
                    .replace("%online%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("%name%", player.getName())
                    .replace("%world%", player.getWorld().getName())
                    .replace("%deaths%", String.valueOf(player.getStatistic(Statistic.DEATHS)))
                    .replace("%footer%", cachedFooter);

            if (zBoard.isPlaceholderAPI()) line = PlaceholderAPI.setPlaceholders(player, line);

            lines.add(CC.translate(line));
        }

        String title = cachedTitle;
        if (zBoard.isPlaceholderAPI()) title = PlaceholderAPI.setPlaceholders(player, title);

        board.updateTitle(title);
        board.updateLines(lines);
    }
}
