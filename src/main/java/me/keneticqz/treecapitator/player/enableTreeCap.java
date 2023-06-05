package me.keneticqz.treecapitator.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class enableTreeCap {
    private final HashMap<UUID,Boolean> Enable = new HashMap<>();

    public void setEnable(Player player,boolean bool){
        this.Enable.put(player.getUniqueId(),bool);
    }

    public boolean isEnable(Player player){
        if(!this.Enable.containsKey(player.getUniqueId())){
            this.Enable.put(player.getUniqueId(),false);
        }
        return this.Enable.get(player.getUniqueId());
    }
}
