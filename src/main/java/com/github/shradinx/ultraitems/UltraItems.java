package com.github.shradinx.ultraitems;

import com.github.shradinx.ultraitems.commands.CreateItemCommand;
import com.github.shradinx.ultraitems.commands.GetItemCommand;
import com.github.shradinx.ultraitems.commands.ReloadCommand;
import com.github.shradinx.ultraitems.listener.ItemUseListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class UltraItems extends JavaPlugin {
    
    @Getter
    private static UltraItems instance;
    
    private ItemManager itemManager;
    
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        itemManager = new ItemManager(this);
        
        // Register events
        getServer().getPluginManager().registerEvents(new ItemUseListener(this), this);
        
        // Register commands
        new GetItemCommand(this, "getitem");
        new CreateItemCommand(this, "createitem");
        new ReloadCommand(this, "uireload");
        
        getLogger().info(String.format("%1$s Enabled!", getPluginMeta().getDisplayName()));
    }
    
    @Override
    public void onDisable() {
        getLogger().info(String.format("%1$s Disabled!", getPluginMeta().getDisplayName()));
    }
}
