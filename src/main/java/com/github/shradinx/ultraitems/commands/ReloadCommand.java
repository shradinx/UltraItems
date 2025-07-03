package com.github.shradinx.ultraitems.commands;

import com.github.shradinx.ultraitems.UltraItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    
    private final UltraItems plugin;
    
    public ReloadCommand(UltraItems plugin, String name) {
        this.plugin = plugin;
        
        plugin.getCommand(name).setExecutor(this);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        sender.sendMessage(Component.text("Reloaded Config!", NamedTextColor.AQUA));
        plugin.getLogger().info("Reloaded Config!");
        plugin.reloadConfig();
        return true;
    }
}
