package hu.zsomborr.board.commands;

import hu.zsomborr.board.utils.CC;
import hu.zsomborr.board.zBoard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BoardCommand implements CommandExecutor {

    private final zBoard plugin = zBoard.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("zboard.admin")) {
            sender.sendMessage(CC.translate(plugin.getConfig().getString("NO-PERMISSION")));
            return true;
        }

        if (!command.getName().equalsIgnoreCase("board")) {
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.saveDefaultConfig();
            sender.sendMessage(CC.translate(plugin.getConfig().getString("RELOAD-MESSAGE")));
            return true;
        }

        for (String line : plugin.getConfig().getStringList("HELP-MESSAGE")) {
            sender.sendMessage(CC.translate(line));
        }

        return true;
    }
}