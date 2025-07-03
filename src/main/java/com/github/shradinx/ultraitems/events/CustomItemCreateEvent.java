package com.github.shradinx.ultraitems.events;

import com.github.shradinx.ultraitems.CustomItem;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class CustomItemCreateEvent extends Event {
    
    private static final HandlerList handlers = new HandlerList();
    
    private final CustomItem item;
    
    public CustomItemCreateEvent(CustomItem item) {
        this.item = item;
    }
    
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
