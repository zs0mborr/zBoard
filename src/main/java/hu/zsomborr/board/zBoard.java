package hu.zsomborr.board;

import hu.zsomborr.board.listeners.CommandListener;
import hu.zsomborr.board.listeners.DamageListener;
import hu.zsomborr.board.listeners.MainListener;
import hu.zsomborr.board.listeners.PearlListener;
import hu.zsomborr.board.sidebar.Provider;
import hu.zsomborr.board.utils.CC;
import hu.zsomborr.board.utils.Utils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class zBoard extends JavaPlugin {

    @Getter
    private static zBoard instance;
    @Getter
    private static boolean placeholderAPI;
    @Getter
    private Provider provider;

    @Override
    public void onEnable() {
        instance = this;
        placeholderAPI = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        provider = new Provider();

        provider.updateTask();
        saveDefaultConfig();
        Utils.registerListeners();
        Utils.registerCommands();
        CC.loadMessage();
    }

    @Override
    public void onDisable() {
        CC.disableMessage();
    }
}
