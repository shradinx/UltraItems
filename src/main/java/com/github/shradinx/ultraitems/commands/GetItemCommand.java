package com.github.shradinx.ultraitems.commands;

import com.github.shradinx.ultraitems.CustomItem;
import com.github.shradinx.ultraitems.UltraItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetItemCommand implements TabExecutor {
    
    private final UltraItems plugin;
    
    private final Component syntax = Component.text("/getitem [namespacedID] (amount)", NamedTextColor.RED);
    private final List<String> amounts = new ArrayList<>(Arrays.asList("1", "8", "16", "32", "64"));
    
    public GetItemCommand(UltraItems plugin, String name) {
        this.plugin = plugin;
        
        plugin.getCommand(name).setExecutor(this);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Component.text("You must be a player to run this command!", NamedTextColor.RED));
            return true;
        }
        
        int amount = 1;
        if (args.length < 1 || args.length > 2) {
            p.sendMessage(syntax);
            return true;
        }
        
        if (args.length == 2) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignored) {
                p.sendMessage(syntax);
                return true;
            }
        }
        
        String namespacedID = args[0];
        List<CustomItem> items = plugin.getItemManager().loadAmount(namespacedID, amount);
        if (items.isEmpty()) {
            p.sendMessage(Component.text("Item not found!", NamedTextColor.RED));
            return true;
        }
        
        p.give(items.stream().map(CustomItem::getItemStack).toList());
        p.sendMessage(
            Component.text(
                String.format("You have been given x%1$s %2$s!", amount, namespacedID),
                NamedTextColor.GREEN
            )
        );
        
        return true;
    }
    
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            String currentInput = args[0].toLowerCase();
            
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("items");
            if (section == null) return List.of();
            
            List<String> keys = section.getKeys(false).stream().toList();
            return keys.stream().filter(t -> t.startsWith(currentInput)).toList();
        } else if (args.length == 2) {
            String currentInput = args[1].toLowerCase();
            return amounts.stream().filter(t -> t.startsWith(currentInput)).toList();
        }
        return List.of();
    }
}
