package me.keneticqz.treecapitator.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class sneakData {
    private final HashMap<UUID,Integer> sneakCount = new HashMap<>();

    public void addCount(Player player,int amount){
        if(!this.sneakCount.containsKey(player.getUniqueId())){
            this.sneakCount.put(player.getUniqueId(),0);
        }
        this.sneakCount.put(player.getUniqueId(),this.sneakCount.get(player.getUniqueId())+amount);
    }

    public void removeCount(Player player,int amount){
        if(!this.sneakCount.containsKey(player.getUniqueId())){
            this.sneakCount.put(player.getUniqueId(),0);
            return;
        }
        if(this.sneakCount.get(player.getUniqueId())<=0){
            System.out.println("There is no count to remove");
            return;
        }
        this.sneakCount.put(player.getUniqueId(),this.sneakCount.get(player.getUniqueId())-amount);
    }

    public void resetCount(Player player){
        this.sneakCount.put(player.getUniqueId(),0);
    }

    public int getCount(Player player){
        return this.sneakCount.get(player.getUniqueId());
    }
}
