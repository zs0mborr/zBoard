package hu.zsomborr.board.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import hu.zsomborr.board.zBoard;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class CC {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String translate(String message) {
        if (message == null) return "";

        Matcher matcher = HEX_PATTERN.matcher(message);
        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder replacement = new StringBuilder("§x");

            for (char c : hex.toCharArray()) {
                replacement.append('§').append(c);
            }

            message = message.replace("&#" + hex, replacement.toString());
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void loadMessage() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(translate(" &6&nzBoard &7(v" + zBoard.getInstance().getDescription().getVersion() + "&7)"));
        Bukkit.getConsoleSender().sendMessage(translate(" &ePlugin has successfully loaded."));
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    public void disableMessage() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(translate(" &6&nzBoard &7(v" + zBoard.getInstance().getDescription().getVersion() + "&7)"));
        Bukkit.getConsoleSender().sendMessage(translate(" &eThe plugin has been disabled."));
        Bukkit.getConsoleSender().sendMessage(" ");
    }
}
