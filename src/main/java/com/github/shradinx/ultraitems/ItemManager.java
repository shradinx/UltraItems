package com.github.shradinx.ultraitems;

import com.github.shradinx.ultraitems.utils.SerializeUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private final NamespacedKey itemTypeKey;
    private final UltraItems plugin;
    
    public ItemManager(UltraItems plugin) {
        this.itemTypeKey = new NamespacedKey(plugin, "item_type");
        this.plugin = plugin;
    }
    
    public void setTag(@NotNull ItemStack item, String tag) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        pdc.set(itemTypeKey, PersistentDataType.STRING, tag);
        item.setItemMeta(meta);
    }
    
    public String getTag(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return "";
        
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.getOrDefault(itemTypeKey, PersistentDataType.STRING, "");
    }
    
    public CustomItem load(String namespacedID) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("items");
        if (section == null) return null;
        
        byte[] arr = (byte[]) section.get(namespacedID, null);
        if (arr == null) return null;
        
        return (CustomItem) SerializeUtils.deserialize(arr);
    }
    
    public List<CustomItem> loadAmount(String namespacedID, int amount) {
        List<CustomItem> items = new ArrayList<>(amount);
        
        CustomItem cItem = load(namespacedID);
        if (cItem != null) {
            for (int i = 0; i < amount; i++) {
                items.add(cItem);
            }
        }
        
        return items;
    }
    
    public CustomItem create(ItemStack item, String namespacedID) {
        CustomItem cItem = new CustomItem(plugin, item, namespacedID);
        setTag(cItem.getItemStack(), namespacedID);
        
        return cItem;
    }
    
    public CustomItem create(ItemStack item, String namespace, String id) {
        CustomItem cItem = new CustomItem(plugin, item, namespace, id);
        setTag(cItem.getItemStack(), cItem.getNamespacedID());
        
        return cItem;
    }
    
    public boolean check(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(itemTypeKey, PersistentDataType.STRING);
    }
    
    public CustomItem create(ItemStack item) {
        String namespacedID = getTag(item);
        if (namespacedID == null || namespacedID.isEmpty()) return null;
        return create(item, namespacedID);
    }
}
