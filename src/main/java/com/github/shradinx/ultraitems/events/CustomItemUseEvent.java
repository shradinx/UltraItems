package com.github.shradinx.ultraitems.events;

import com.github.shradinx.ultraitems.CustomItem;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.jetbrains.annotations.NotNull;

@Getter
public class CustomItemUseEvent extends Event implements Cancellable {
    
    private static final HandlerList handlers = new HandlerList();
    
    @Getter(AccessLevel.NONE)
    private boolean cancelled;
    
    private final Player player;
    private final CustomItem item;
    private final Action action;
    
    public CustomItemUseEvent(Player player, CustomItem item, Action action) {
        this.player = player;
        this.item = item;
        this.action = action;
    }
    
    @Override
    public boolean isCancelled() {
        return cancelled;
    }
    
    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
    
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
