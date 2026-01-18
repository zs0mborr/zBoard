package hu.zsomborr.board.utils;

import hu.zsomborr.board.commands.BoardCommand;
import hu.zsomborr.board.listeners.CommandListener;
import hu.zsomborr.board.listeners.DamageListener;
import hu.zsomborr.board.listeners.MainListener;
import hu.zsomborr.board.listeners.PearlListener;
import hu.zsomborr.board.zBoard;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    private final zBoard plugin = zBoard.getInstance();

    public void registerCommands() {
        plugin.getCommand("board").setExecutor(new BoardCommand());
    }

    public void registerListeners() {
        plugin.getServer().getPluginManager().registerEvents(new MainListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PearlListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DamageListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CommandListener(), plugin);
    }
}
