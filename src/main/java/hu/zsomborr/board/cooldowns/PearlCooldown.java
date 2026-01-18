package hu.zsomborr.board.cooldowns;

import hu.zsomborr.board.zBoard;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class PearlCooldown {

    @Getter
    private final Map<UUID, Long> pearlCooldown = new HashMap<>();
    @Getter
    private static final long COOLDOWN_TIME = zBoard.getInstance().getConfig().getInt("COOLDOWNS.PEARL") * 1000L;

    public String formatRemaining(long remainingMillis) {
        return String.format(Locale.US, "%.1f", remainingMillis / 1000.0);
    }

    public void remove(Player player) {
        pearlCooldown.remove(player.getUniqueId());
    }

    public long getPearlCooldown(Player player) {
        UUID uuid = player.getUniqueId();

        if (!pearlCooldown.containsKey(uuid)) {
            return 0L;
        }

        long expire = pearlCooldown.get(uuid) + COOLDOWN_TIME;
        long remaining = expire - System.currentTimeMillis();

        return Math.max(0L, remaining);
    }

    public boolean hasPearlCooldown(UUID uuid) {
        if (!pearlCooldown.containsKey(uuid)) {
            return false;
        }

        long expire = pearlCooldown.get(uuid) + COOLDOWN_TIME;
        return expire > System.currentTimeMillis();
    }
}
