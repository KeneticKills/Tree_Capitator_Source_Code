package me.keneticqz.treecapitator.events;

import me.keneticqz.treecapitator.custom_event.PlayerLoaded;
import me.keneticqz.treecapitator.player.enableTreeCap;
import me.keneticqz.treecapitator.player.sneakData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class PlayerLogin implements Listener {

    private Plugin plugin ;
    private HashMap<UUID,BukkitTask> event;
    private final sneakData sneakData ;
    private final enableTreeCap enableTreeCap;

    public PlayerLogin(Plugin plugin,sneakData sneakData,enableTreeCap enableTreeCap){
        this.plugin = plugin;
        this.event = new HashMap<>();
        this.sneakData = sneakData;
        this.enableTreeCap = enableTreeCap;
    }

    @EventHandler
    public void playerLogin(PlayerJoinEvent playerJoinEvent){
        Player player = playerJoinEvent.getPlayer();
        BukkitTask bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                getServer().getPluginManager().callEvent(new PlayerLoaded(player));
            }
        }.runTaskTimer(this.plugin,0L,1L);
        this.event.put(player.getUniqueId(),bukkitTask);
    }

    @EventHandler
    public void playerLogout(PlayerQuitEvent playerQuitEvent){
        Player player = playerQuitEvent.getPlayer();
        if(!this.event.containsKey(player.getUniqueId())){
            return;
        }
        this.event.get(player.getUniqueId()).cancel();
        this.enableTreeCap.setEnable(player,false);
        this.sneakData.resetCount(player);
    }
}
