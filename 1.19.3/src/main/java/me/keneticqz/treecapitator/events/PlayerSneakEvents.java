package me.keneticqz.treecapitator.events;

import me.keneticqz.treecapitator.player.enableTreeCap;
import me.keneticqz.treecapitator.player.sneakData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerSneakEvents implements Listener {
    private final sneakData sneakData;
    private final enableTreeCap enableTreeCap;
    private final HashMap<UUID, Long> inTime = new HashMap<>();
    private final Material[] Axe = {Material.GOLDEN_AXE,Material.WOODEN_AXE,Material.STONE_AXE,Material.IRON_AXE,
    Material.DIAMOND_AXE,Material.NETHERITE_AXE};

    public PlayerSneakEvents(sneakData sneakData, enableTreeCap enableTreeCap){
        this.sneakData = sneakData;
        this.enableTreeCap = enableTreeCap;
    }

    @EventHandler
    public void playerSneak(PlayerToggleSneakEvent e){
        Player player = e.getPlayer();
        boolean pass = false;
        for(Material material : Axe){
            if(material.equals(player.getInventory().getItemInMainHand().getType())){
                pass = true;
                break;
            }
        }
        if(!pass){
            return;
        }
        if(!inTime.containsKey(player.getUniqueId())){
            inTime.put(player.getUniqueId(),System.currentTimeMillis());
        }
        if(System.currentTimeMillis() - inTime.get(player.getUniqueId()) > 5000){
            sneakData.resetCount(player);
            enableTreeCap.setEnable(player,false);
        }
        sneakData.addCount(player,1);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("Test"));
        if(sneakData.getCount(player) < 5){
            return;
        }
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent("Now Enable"));
        enableTreeCap.setEnable(player,true);
    }
}
