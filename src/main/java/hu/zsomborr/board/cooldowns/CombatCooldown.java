package hu.zsomborr.board.cooldowns;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import hu.zsomborr.board.zBoard;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class CombatCooldown {

    @Getter
    private final Map<UUID, Long> combatCooldown = new HashMap<>();
    @Getter
    private static final long COOLDOWN_TIME = zBoard.getInstance().getConfig().getInt("COOLDOWNS.COMBAT") * 1000L;

    public String formatRemaining(long remainingMillis) {
        return String.format(Locale.US, "%.1f", remainingMillis / 1000.0);
    }

    public void remove(Player player) {
        combatCooldown.remove(player.getUniqueId());
    }

    public long getCombatCooldown(Player player) {
        UUID uuid = player.getUniqueId();

        if (!combatCooldown.containsKey(uuid)) {
            return 0L;
        }

        long expire = combatCooldown.get(uuid) + COOLDOWN_TIME;
        long remaining = expire - System.currentTimeMillis();

        return Math.max(0L, remaining);
    }

    public boolean inCombat(UUID uuid) {
        if (!combatCooldown.containsKey(uuid)) {
            return false;
        }

        long expire = combatCooldown.get(uuid) + COOLDOWN_TIME;
        return expire > System.currentTimeMillis();
    }
}
