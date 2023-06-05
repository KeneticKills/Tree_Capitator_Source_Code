package me.keneticqz.treecapitator;

import me.keneticqz.treecapitator.events.PlayerChangeSlot;
import me.keneticqz.treecapitator.events.PlayerSneakEvents;
import me.keneticqz.treecapitator.player.enableTreeCap;
import me.keneticqz.treecapitator.player.sneakData;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TreeCapitator extends JavaPlugin {

    @Override
    public void onEnable() {
        sneakData sneakData = new sneakData();
        enableTreeCap enableTreeCap = new enableTreeCap();
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerSneakEvents(sneakData,enableTreeCap),this);
        pluginManager.registerEvents(new PlayerChangeSlot(enableTreeCap),this);
    }
}
