package me.keneticqz.treecapitator.events;

import me.keneticqz.treecapitator.player.enableTreeCap;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class PlayerChangeSlot implements Listener {

    private final Material[] Axe = {Material.GOLDEN_AXE,Material.WOODEN_AXE,Material.STONE_AXE,Material.IRON_AXE,
            Material.DIAMOND_AXE,Material.NETHERITE_AXE};
    private final enableTreeCap enableTreeCap;

    public PlayerChangeSlot(enableTreeCap enableTreeCap){
        this.enableTreeCap = enableTreeCap;
    }

    @EventHandler
    public void SlotChange(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
        boolean pass = false;
        if(!this.enableTreeCap.isEnable(player)){
            return;
        }
        for(Material material:Axe){
            if(material.equals(player.getInventory().getItemInMainHand().getType())){
                pass=true;
                break;
            }
        }
        if(!pass){
            this.enableTreeCap.setEnable(player,false);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("Now Disable"));
        }
    }
}
