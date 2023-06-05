package me.keneticqz.treecapitator.events;

import me.keneticqz.treecapitator.custom_event.PlayerLoaded;
import me.keneticqz.treecapitator.player.enableTreeCap;
import me.keneticqz.treecapitator.player.sneakData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.world.EntitiesLoadEvent;

import java.util.List;

public class PlayerChangeSlot implements Listener {

    private final Material[] Axe = {Material.GOLDEN_AXE,Material.WOODEN_AXE,Material.STONE_AXE,Material.IRON_AXE,
            Material.DIAMOND_AXE,Material.NETHERITE_AXE};
    private final enableTreeCap enableTreeCap;
    private final sneakData sneakData;

    public PlayerChangeSlot(sneakData sneakData, enableTreeCap enableTreeCap){
        this.sneakData = sneakData;
        this.enableTreeCap = enableTreeCap;
    }
    /*
    @EventHandler
    public void SlotChange(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
        if(!this.enableTreeCap.isEnable(player)){
            return;
        }
        boolean pass = false;
        for(Material material : this.Axe){
            if(player.getInventory().getItem(e.getNewSlot()) == null){
                break;
            }
            else if(material.equals(player.getInventory().getItem(e.getNewSlot()).getType())){
                pass = true;
                break;
            }
        }
        if(!pass){
            this.enableTreeCap.setEnable(player,false);
            this.sneakData.resetCount(player);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.DARK_GREEN+"Tree Capitator"+
                    ChatColor.RESET+" ["+ ChatColor.RED+"Disable" + org.bukkit.ChatColor.RESET+"]"
            ));
        }
    }*/

    @EventHandler
    public void WorldLoaded(PlayerLoaded e){
        Player player = e.getPlayer();
        if (!this.enableTreeCap.isEnable(player)) {
            return;
        }
        boolean pass = false;
        for (Material material : this.Axe) {
            if (player.getInventory().getItemInMainHand().getType().equals(material)) {
                pass = true;
                break;
            }
        }
        if (!pass) {
            this.enableTreeCap.setEnable(player, false);
            this.sneakData.resetCount(player);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN + "Tree Capitator" +
                    ChatColor.RESET + " [" + ChatColor.RED + "Disable" + org.bukkit.ChatColor.RESET + "]"
            ));
        }
    }
}
