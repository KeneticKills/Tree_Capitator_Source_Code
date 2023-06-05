package me.keneticqz.treecapitator.custom_event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoaded extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player ;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public PlayerLoaded(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
