package me.keneticqz.treecapitator.events;

import me.keneticqz.treecapitator.player.enableTreeCap;
import me.keneticqz.treecapitator.player.sneakData;
import org.bukkit.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
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
        if(this.enableTreeCap.isEnable(player)){
            return;
        }
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
        if(System.currentTimeMillis() - inTime.get(player.getUniqueId()) > 3000){
            sneakData.resetCount(player);
        }
        this.sneakData.addCount(player,1);
        this.inTime.put(player.getUniqueId(),System.currentTimeMillis());
        switch (this.sneakData.getCount(player)) {
            case 0:
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN+"Tree Capitator"+
                        ChatColor.RESET+" ["+ChatColor.RED+"❚❚❚❚❚"+ChatColor.RESET+"]"
                ));
                break;
            case 10:
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN+"Tree Capitator"+
                        ChatColor.RESET+" ["+ ChatColor.GREEN+"❚❚❚❚❚"+ChatColor.RESET+"]"
                ));
                break;
            default:
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN+"Tree Capitator"+
                        ChatColor.RESET+" ["+ ChatColor.GREEN+"❚".repeat(this.sneakData.getCount(player)/2)+
                        ChatColor.RED+"❚".repeat((11-this.sneakData.getCount(player))/2)+ChatColor.RESET+"]"
                ));
                break;
        }
        if(this.sneakData.getCount(player) < 10){
            return;
        }
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1.0F, 0.5F);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.DARK_GREEN+"Tree Capitator"+
                ChatColor.RESET+" ["+ ChatColor.GREEN+"Enable" + ChatColor.RESET+"]"
        ));
        enableTreeCap.setEnable(player,true);
    }
}
