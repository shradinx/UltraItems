package com.github.shradinx.ultraitems.commands;

import com.github.shradinx.ultraitems.CustomItem;
import com.github.shradinx.ultraitems.UltraItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CreateItemCommand implements TabExecutor {
    
    private final UltraItems plugin;
    private final Component syntax = Component.text("Usage: ", NamedTextColor.RED)
        .append(Component.text("/createitem [namespacedID]", NamedTextColor.RED))
        .append(Component.newline())
        .append(Component.text(" or", NamedTextColor.GRAY))
        .append(Component.newline())
        .append(Component.text("/createitem [namespace] [id]", NamedTextColor.RED));
    
    public CreateItemCommand(UltraItems plugin, String name) {
        this.plugin = plugin;
        
        plugin.getCommand(name).setExecutor(this);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Component.text("You must be a player to run this command!", NamedTextColor.RED));
            return true;
        }
        
        if (args.length < 1 || args.length > 2) {
            p.sendMessage(syntax);
            return true;
        }
        
        String namespaceID = args[0];
        if (args.length == 2) {
            namespaceID = args[0] + ":" + args[1];
        }
        
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.isEmpty()) return true;
        
        CustomItem cItem = plugin.getItemManager().create(item, namespaceID);
        p.getInventory().setItemInMainHand(cItem.getItemStack());
        p.sendMessage(Component.text(String.format("Created custom item %s!", namespaceID), NamedTextColor.GREEN));
        return true;
    }
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
