package com.github.shradinx.ultraitems;

import com.github.shradinx.ultraitems.events.CustomItemCreateEvent;
import com.github.shradinx.ultraitems.utils.SerializeUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Getter
public class CustomItem {
    @Getter(AccessLevel.NONE)
    private final UltraItems plugin;
    
    private final ItemStack itemStack;
    private final String namespace;
    private final String id;
    
    protected CustomItem(UltraItems plugin, @NotNull ItemStack itemStack, String namespacedID) {
        this.itemStack = itemStack;
        this.plugin = plugin;
        
        String[] spl = namespacedID.split(":");
        this.namespace = spl[0];
        this.id = (spl.length == 2) ? spl[1] : "";
        
        callEvent();
        save();
    }
    
    protected CustomItem(UltraItems plugin, @NotNull ItemStack itemStack, String namespace, String id) {
        this.itemStack = itemStack;
        this.namespace = namespace;
        this.id = id;
        this.plugin = plugin;
        
        callEvent();
        save();
    }
    
    private void callEvent() {
        CustomItemCreateEvent event = new CustomItemCreateEvent(this);
        UltraItems.getInstance().getServer().getPluginManager().callEvent(event);
    }
    
    public void save() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("items");
        if (section == null) return;
        
        section.set(getNamespacedID(), SerializeUtils.serialize(itemStack));
        plugin.getConfig().set("items", section);
        plugin.saveConfig();
    }
    
    public String getNamespacedID() {
        return namespace + ":" + id;
    }
    
    
}
