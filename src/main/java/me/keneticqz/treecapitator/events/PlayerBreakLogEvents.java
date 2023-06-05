package me.keneticqz.treecapitator.events;

import me.keneticqz.treecapitator.player.enableTreeCap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerBreakLogEvents implements Listener {

    private final enableTreeCap enableTreeCap ;
    private final Material[] whitelist = {Material.OAK_LOG,Material.SPRUCE_LOG,Material.BIRCH_LOG,Material.JUNGLE_LOG,
    Material.ACACIA_LOG,Material.DARK_OAK_LOG,Material.MANGROVE_LOG,Material.CRIMSON_STEM,Material.WARPED_STEM};

    public PlayerBreakLogEvents(enableTreeCap enableTreeCap){
        this.enableTreeCap = enableTreeCap;
    }

    private List<Block> getNearbyLog(Location location){
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - 2; x <= location.getBlockX() + 2; x++) {
            for(int y = location.getBlockY() - 2; y <= location.getBlockY() + 2; y++) {
                for(int z = location.getBlockZ() - 2; z <= location.getBlockZ() + 2; z++) {
                    for(Material m : this.whitelist) {
                        if (location.getWorld() != null && location.getWorld().getBlockAt(x,y,z).getType().equals(m)) {
                            blocks.add(location.getWorld().getBlockAt(x, y, z));
                        }
                    }
                }
            }
        }
        return blocks;
    }

    private void chainBlockBreak(Player player, Block block, int Count, ItemMeta itemMeta){
        List<Block> nearbyBlock = this.getNearbyLog(block.getLocation());
        Damageable damageable = (Damageable) itemMeta;
        int MaxDurability = player.getInventory().getItemInMainHand().getType().getMaxDurability();
        assert damageable != null;
        if(!(damageable.getDamage()+Count < MaxDurability)){
            return;
        }
        if(nearbyBlock.isEmpty()){
            return;
        }
        block.breakNaturally(player.getInventory().getItemInMainHand());
        for(Block b : nearbyBlock){
            b.breakNaturally(player.getInventory().getItemInMainHand());
            int damage = RandomDurability(itemMeta);
            damageable.setDamage(damageable.getDamage()+damage);
            if(!(damageable.getDamage()+damage < MaxDurability)){
                return;
            }
        }
        for(Block b : nearbyBlock){
            chainBlockBreak(player,b,RandomDurability(itemMeta),itemMeta);
        }
    }

    private int RandomDurability(ItemMeta itemMeta){
        double random = Math.random();
        if(itemMeta.isUnbreakable()){
            return 0;
        }
        return (random <= (((double)1)/(itemMeta.getEnchantLevel(Enchantment.DURABILITY)+1))) ? 1 :  0;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerBreakBlock(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(!this.enableTreeCap.isEnable(player)){
            return;
        }
        boolean pass = false;
        for(Material material : this.whitelist){
            if(e.getBlock().getType().equals(material)){
                pass = true;
                break;
            }
        }
        if(!pass){
            return;
        }
        ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
        assert meta != null;
        int Count = RandomDurability(meta);
        this.chainBlockBreak(player,e.getBlock(),Count,meta);
        player.getInventory().getItemInMainHand().setItemMeta(meta);
    }
}
