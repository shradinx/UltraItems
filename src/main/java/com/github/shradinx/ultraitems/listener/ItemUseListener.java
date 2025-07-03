package com.github.shradinx.ultraitems.listener;

import com.github.shradinx.ultraitems.CustomItem;
import com.github.shradinx.ultraitems.UltraItems;
import com.github.shradinx.ultraitems.events.CustomItemUseEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemUseListener implements Listener {
    
    private final UltraItems plugin;
    
    public ItemUseListener(UltraItems plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY) return;
        
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        
        CustomItem cItem = plugin.getItemManager().create(item);
        if (cItem == null) return;
        
        CustomItemUseEvent e = new CustomItemUseEvent(player, cItem, event.getAction());
        plugin.getServer().getPluginManager().callEvent(e);
    }
}
